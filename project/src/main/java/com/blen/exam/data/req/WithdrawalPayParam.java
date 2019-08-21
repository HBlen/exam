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
public class WithdrawalPayParam {

  private Long id;

  private String txhash;

  private Long height;

  private BigDecimal realFee;
}
