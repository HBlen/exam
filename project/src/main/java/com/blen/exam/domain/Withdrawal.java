package com.blen.exam.domain;

import java.math.BigDecimal;

import sun.jvm.hotspot.debugger.cdbg.Sym;

import com.blen.exam.data.req.WithdrawalCreateParam;
import com.sun.tools.javac.code.Symtab;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Withdrawal {

  private Long id;

  private Long userId;

  private String address;

  private String currency;

  private String txhash;

  private BigDecimal amount;

  private BigDecimal fee;

  private BigDecimal realFee;

  private Integer confirmTimes;

  private Long height;

  private String extra;

  private Long createdAt;

  private Long updatedAt;

  private Long version;

  private Integer state;

  public static Withdrawal buildFromCreateParam(WithdrawalCreateParam createParam){
    return Withdrawal.builder()
        .userId(createParam.getUserId())
        .address(createParam.getAddress())
        .currency(createParam.getCurrency())
        .amount(createParam.getAmount())
        .fee(createParam.getFee())
        .createdAt(System.currentTimeMillis())
        .updatedAt(System.currentTimeMillis())
        .state(1)
        .build();
  }
}
