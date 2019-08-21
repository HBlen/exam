package com.blen.exam.data.req;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalCreateParam {

  private Long userId;

  private String currency;

  private BigDecimal amount;

  private BigDecimal fee;

  private String address;
}
