package com.blen.exam.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyInfo {

  private Long id;

  private String currencyNo;

  private Long version;

  private Integer state;
}
