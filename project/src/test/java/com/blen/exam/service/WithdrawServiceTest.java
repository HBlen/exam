package com.blen.exam.service;

import java.math.BigDecimal;

import com.blen.exam.BaseTest;
import com.blen.exam.data.req.WithdrawalCreateParam;
import com.blen.exam.data.req.WithdrawalFetchParam;
import com.blen.exam.data.req.WithdrawalPayParam;
import com.blen.exam.domain.UserAccount;
import com.blen.exam.domain.UserInfo;
import com.blen.exam.domain.UserWithdrawAddress;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WithdrawServiceTest extends BaseTest {

  static Long userId;
  static Long orderId;
  @Autowired private WithdrawService withdrawService;

  private WithdrawalCreateParam createParam(
      Long userId, String currency, BigDecimal amount, BigDecimal fee, String address) {
    return WithdrawalCreateParam.builder()
        .userId(userId)
        .currency(currency)
        .amount(amount)
        .fee(fee)
        .address(address)
        .build();
  }

  private UserInfo createUserInfo(
      String userName, String phone, String extra, Long createdAt, Long updatedAt, Integer state) {
    return UserInfo.builder()
        .userName(userName)
        .phone(phone)
        .extra(extra)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .state(state)
        .build();
  }

  private UserAccount createUserAccount(
      Long userId,
      String currency,
      BigDecimal balance,
      String extra,
      Long createdAt,
      Long updatedAt,
      Integer state) {
    return UserAccount.builder()
        .userId(userId)
        .currency(currency)
        .balance(balance)
        .extra(extra)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .state(state)
        .build();
  }

  private UserWithdrawAddress createUserWithdrawAddress(
      Long userId, String address, Integer state) {
    return UserWithdrawAddress.builder().userId(userId).address(address).state(state).build();
  }

  /**
   * 准备数据
   *
   * @testCase 1、创建提现单
   */
  @Test
  public void a() {
    UserInfo userInfo =
        createUserInfo(
            "hhh", "10000000", "test", System.currentTimeMillis(), System.currentTimeMillis(), 1);
    userId = withdrawService.insertUserInfo(userInfo);
    assertTrue(userId > 0);

    UserAccount userAccount =
        createUserAccount(
            userId,
            "btc",
            BigDecimal.valueOf(500),
            "extra",
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            1);
    Long userAccountId = withdrawService.insertUserAccount(userAccount);
    assertTrue(userAccountId > 0);

    UserWithdrawAddress userWithdrawAddress = createUserWithdrawAddress(userId, "address", 1);
    Long addressId = withdrawService.insertUserWithdrawAddress(userWithdrawAddress);
    assertTrue(addressId > 0);

    WithdrawalCreateParam createParam =
        createParam(userId, "btc", BigDecimal.valueOf(70), BigDecimal.valueOf(0.3), "address");
    orderId = withdrawService.create(createParam);
    assertTrue(orderId > 0); // testCase 1
  }

  /**
   * @testCase 1、账户不存在
   * @testCase 2、余额不足
   * @testCase 3、地址不合法
   * @testCase 4、总提现超过额度100
   * @testCase 5、创建提现单
   */
  @Test
  public void b_testAccountNon() {
    WithdrawalCreateParam createParam =
        createParam(userId + 1, "btc", BigDecimal.valueOf(20), BigDecimal.valueOf(0.3), "address");
    try {
      withdrawService.create(createParam);
      Assert.fail();
    } catch (RuntimeException e) {
      assertEquals("没有该账户", e.getMessage());
    }
  }

  @Test
  public void c_testBalance() {
    WithdrawalCreateParam createParam =
        createParam(userId, "btc", BigDecimal.valueOf(2000), BigDecimal.valueOf(0.3), "address");
    try {
      withdrawService.create(createParam);
      Assert.fail();
    } catch (RuntimeException e) {
      assertEquals("余额不足", e.getMessage());
    }
  }

  @Test
  public void d_testAddress() {
    WithdrawalCreateParam createParam =
        createParam(
            userId, "btc", BigDecimal.valueOf(20), BigDecimal.valueOf(0.3), "addressssssss");
    try {
      withdrawService.create(createParam);
      Assert.fail();
    } catch (RuntimeException e) {
      assertEquals("地址不合法", e.getMessage());
    }
  }

  @Test
  public void e_testAmount() {
    WithdrawalCreateParam createParam =
        createParam(userId, "btc", BigDecimal.valueOf(150), BigDecimal.valueOf(0.3), "address");
    try {
      withdrawService.create(createParam);
      Assert.fail();
    } catch (RuntimeException e) {
      assertEquals("当日总提现超过额度100", e.getMessage());
    }
  }

  private WithdrawalFetchParam fetchParam(Long userId, Long id) {
    return WithdrawalFetchParam.builder().id(id).userId(userId).build();
  }

  /**
   * @testCase 1、无此提现单
   * @testCase 2、用户错误
   * @testCase 3、正常打款
   */
  @Test
  public void g_test_order_non() {
    WithdrawalFetchParam fetchParam = fetchParam(userId, orderId + 10000);
    try {
      withdrawService.fetch(fetchParam);
      Assert.fail();
    } catch (RuntimeException e) {
      assertEquals("无此提现单", e.getMessage());
    }
  }

  @Test
  public void h_test_userId_error() {

    WithdrawalFetchParam fetchParam = fetchParam(userId + 10000, orderId);
    try {
      withdrawService.fetch(fetchParam);
      Assert.fail();
    } catch (RuntimeException e) {
      assertEquals("用户错误", e.getMessage());
    }
  }

  @Test
  public void i_fetch() {

    WithdrawalFetchParam fetchParam = fetchParam(userId, orderId);
    int resp = withdrawService.fetch(fetchParam);
    assertTrue(resp > 0);
  }

  private WithdrawalPayParam createPayParam(
      Long id, String txhash, Long height, BigDecimal realFee) {
    return WithdrawalPayParam.builder()
        .id(id)
        .txhash(txhash)
        .height(height)
        .realFee(realFee)
        .build();
  }

  /** @testCase 1、无此提现单 */
  @Test
  public void j_test_order_non() {
    WithdrawalPayParam payParam = createPayParam(10000L, "txhash", 1000L, BigDecimal.valueOf(0.4));
    try {
      withdrawService.pay(payParam);
      Assert.fail();
    } catch (RuntimeException e) {
      assertEquals("无此提现单", e.getMessage());
    }
  }

  /** @testCase 1、打款 */
  @Test
  public void k_test_Amount() {

    WithdrawalPayParam payParam = createPayParam(orderId, "txhash", 1000L, BigDecimal.valueOf(0.4));
    Integer resp = withdrawService.pay(payParam);
    assertTrue(resp > 0);
  }
}
