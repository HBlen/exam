package com.blen.exam.domain;

import java.math.BigDecimal;

import com.blen.exam.data.req.WithdrawalCreateParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

  private Integer discard;

  public static Withdrawal buildFromCreateParam(WithdrawalCreateParam createParam) {
    return Withdrawal.builder()
        .userId(createParam.getUserId())
        .address(createParam.getAddress())
        .currency(createParam.getCurrency())
        .amount(createParam.getAmount())
        .fee(createParam.getFee())
        .createdAt(System.currentTimeMillis())
        .updatedAt(System.currentTimeMillis())
        .state(1)
        .discard(1)
        .build();
  }
}
