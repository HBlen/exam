package com.blen.exam.service;

import java.math.BigDecimal;

import com.blen.exam.ExamApplicationTests;
import com.blen.exam.data.req.WithdrawalCreateParam;
import com.blen.exam.data.req.WithdrawalFetchParam;
import com.blen.exam.data.req.WithdrawalPayParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertTrue;

public class WithdrawServiceTest extends ExamApplicationTests {

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

  @Test
  public void create() {
    WithdrawalCreateParam createParam =
        createParam(1000L, "btc", BigDecimal.valueOf(20), BigDecimal.valueOf(0.3), "address");
    long resp = withdrawService.create(createParam);
    assertTrue(resp > 0);
  }

  private WithdrawalFetchParam fetchParam(Long userId, Long id) {
    return WithdrawalFetchParam.builder().id(id).userId(userId).build();
  }

  @Test
  public void fetch() {
    WithdrawalFetchParam fetchParam = fetchParam(1000L, 1L);
    withdrawService.fetch(fetchParam);
  }

  private WithdrawalPayParam payParam(Long id, String txhash, Long height, BigDecimal realfee) {
    return WithdrawalPayParam.builder()
        .id(id)
        .txhash(txhash)
        .height(height)
        .realFee(realfee)
        .build();
  }
}
