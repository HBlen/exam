package com.blen.exam.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.blen.exam.dao.UserAccountMapper;
import com.blen.exam.dao.UserInfoMapper;
import com.blen.exam.dao.UserWithdrawAddressMapper;
import com.blen.exam.dao.WithdrawalMapper;
import com.blen.exam.data.req.WithdrawalCreateParam;
import com.blen.exam.data.req.WithdrawalFetchParam;
import com.blen.exam.data.req.WithdrawalPayParam;
import com.blen.exam.domain.UserAccount;
import com.blen.exam.domain.UserInfo;
import com.blen.exam.domain.UserWithdrawAddress;
import com.blen.exam.domain.Withdrawal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class WithdrawService {
  @Autowired private WithdrawalMapper withdrawalMapper;
  @Autowired private UserWithdrawAddressMapper userWithdrawAddressMapper;
  @Autowired private UserAccountMapper userAccountMapper;
  @Autowired private UserInfoMapper userInfoMapper;

  public Long create(WithdrawalCreateParam createParam) {

    checkBalance(
        createParam.getUserId(),
        createParam.getCurrency(),
        createParam.getAmount(),
        createParam.getFee());

    checkAddress(createParam.getUserId(), createParam.getAddress());
    checkWithdrawalLimits(
        createParam.getUserId(), createParam.getCurrency(), createParam.getAmount());

    Withdrawal withdrawal = Withdrawal.buildFromCreateParam(createParam);
    try {
      withdrawalMapper.insert(withdrawal);
      return withdrawal.getId();
    } catch (DuplicateKeyException ex) {
      throw new DuplicateKeyException("提现单重复");
    }
  }

  /**
   * 校验账户余额是否够提现
   *
   * @param userId 用户id
   * @param currency 币种
   * @param thisAmount 本次提现金额
   * @param fee 手续费
   * @return 提现完成后的余额
   */
  private BigDecimal checkBalance(
      Long userId, String currency, BigDecimal thisAmount, BigDecimal fee) {
    UserAccount userAccount = userAccountMapper.selectByUserIdAndCurrency(userId, currency);
    if (userAccount == null) {
      throw new RuntimeException("没有该账户");
    }
    BigDecimal balance = userAccount.getBalance().subtract(thisAmount.add(fee));
    if (balance.compareTo(BigDecimal.valueOf(0)) < 0) {
      throw new RuntimeException("余额不足");
    }
    return balance;
  }

  /**
   * 校验提现地址是否合法
   *
   * @param userId 用户id
   * @param address 提现地址
   * @throws RuntimeException
   */
  private void checkAddress(Long userId, String address) throws RuntimeException {
    if (userWithdrawAddressMapper.selectByUserIdAndAddress(userId, address) == null) {
      throw new RuntimeException("地址不合法");
    }
  }

  /**
   * 校验当日提现总额度是否超过限额
   *
   * @param userId 用户id
   * @param currency 币种
   * @param thisAmount 本次提现额
   * @throws RuntimeException
   */
  private void checkWithdrawalLimits(Long userId, String currency, BigDecimal thisAmount)
      throws RuntimeException {

    // 获取某天的起止时间，转为ms
    Calendar dayStart = Calendar.getInstance();
    dayStart.setTime(new Date());
    dayStart.set(Calendar.HOUR_OF_DAY, 0);
    dayStart.set(Calendar.MINUTE, 0);
    dayStart.set(Calendar.SECOND, 0);
    long todayStart = dayStart.getTimeInMillis();
    long todayEnd = todayStart + 24 * 60 * 60 * 1000L;

    List<Withdrawal> withdrawals =
        withdrawalMapper.selectByUserIdAndUpdateAt(userId, currency, todayStart, todayEnd, 3);
    Double preAmount =
        Optional.ofNullable(
                withdrawals.stream().mapToDouble(w -> w.getAmount().doubleValue()).sum())
            .orElse(0D);
    if (preAmount + thisAmount.doubleValue() > 100) {
      throw new RuntimeException("当日总提现超过额度100");
    }
  }

  public Integer fetch(WithdrawalFetchParam fetchParam) {
    Withdrawal withdrawal = withdrawalMapper.selectByIdAndState(fetchParam.getId(), 1);
    if (withdrawal == null) {
      throw new RuntimeException("无此提现单");
    }
    if (!fetchParam.getUserId().equals(withdrawal.getUserId())) {
      throw new RuntimeException("用户错误");
    }
    return withdrawalMapper.updateStateById(fetchParam.getId(), 2);
  }

  public Integer pay(WithdrawalPayParam payParam) {
    Withdrawal withdrawal = withdrawalMapper.selectByIdAndState(payParam.getId(), 2);
    if (withdrawal == null) {
      throw new RuntimeException("无此提现单");
    }
    BigDecimal balance =
        checkBalance(
            withdrawal.getUserId(),
            withdrawal.getCurrency(),
            withdrawal.getAmount(),
            payParam.getRealFee());
    checkWithdrawalLimits(withdrawal.getUserId(), withdrawal.getCurrency(), withdrawal.getAmount());
    return (userAccountMapper.updateByUserIdAndCurrency(
            withdrawal.getUserId(), withdrawal.getCurrency(), balance)
        & withdrawalMapper.updateTxhashHeightAndRealFee(
            payParam.getId(), payParam.getTxhash(), payParam.getHeight(), payParam.getRealFee())
        & withdrawalMapper.updateStateById(payParam.getId(), 3));
  }

  public Long insertUserInfo(UserInfo userInfo) {
    return userInfoMapper.insert(userInfo);
  }

  public Long insertUserAccount(UserAccount userAccount) {
    return userAccountMapper.insert(userAccount);
  }

  public Long insertUserWithdrawAddress(UserWithdrawAddress userWithdrawAddress) {
    return userWithdrawAddressMapper.insert(userWithdrawAddress);
  }
}
