package com.blen.exam.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.blen.exam.dao.UserWithdrawAddressMapper;
import com.blen.exam.dao.WithdrawalMapper;
import com.blen.exam.data.req.WithdrawalCreateParam;
import com.blen.exam.data.req.WithdrawalFetchParam;
import com.blen.exam.data.req.WithdrawalPayParam;
import com.blen.exam.domain.Withdrawal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawService {

  @Autowired private WithdrawalMapper withdrawalMapper;

  @Autowired private UserWithdrawAddressMapper userWithdrawAddressMapper;

  public Long create(WithdrawalCreateParam createParam) {

    checkAddress(createParam.getUserId(), createParam.getAddress());
    checkWithdrawalLimits(
        createParam.getUserId(), createParam.getCurrency(), createParam.getAmount());

    Withdrawal withdrawal = Withdrawal.buildFromCreateParam(createParam);
    withdrawalMapper.insert(withdrawal);
    return withdrawal.getId();
  }

  private void checkAddress(Long userId, String address) throws RuntimeException {
    if (userWithdrawAddressMapper.selectByUserIdAndAddress(userId, address) == null) {
      throw new RuntimeException("地址不合法");
    }
  }

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
    Double preAmount = withdrawals.stream().mapToDouble(w -> w.getAmount().doubleValue()).sum();
    if (preAmount + thisAmount.doubleValue() > 100) {
      throw new RuntimeException("总提现超过额度100");
    }
  }

  public void fetch(WithdrawalFetchParam fetchParam) {
    Withdrawal withdrawal = withdrawalMapper.selectByIdAndState(fetchParam.getId(), 1);
    if (withdrawal == null) {
      throw new RuntimeException("无此提现单");
    }
    if (!fetchParam.getUserId().equals(withdrawal.getUserId())) {
      throw new RuntimeException("用户错误");
    }
    withdrawalMapper.updateStateById(fetchParam.getId(), 2);
  }

  public void pay(WithdrawalPayParam payParam) {
    Withdrawal withdrawal = withdrawalMapper.selectByIdAndState(payParam.getId(), 2);
    if (withdrawal == null) {
      throw new RuntimeException("无此提现单");
    }
    withdrawalMapper.updateTxhashHeightAndRealFee(
        payParam.getId(), payParam.getTxhash(), payParam.getHeight(), payParam.getRealFee());
    withdrawalMapper.updateStateById(payParam.getId(), 3);
    //todo:账户减少余额
  }
}
