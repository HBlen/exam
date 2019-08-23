package com.blen.exam.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {

  private Long id;

  private Long userId;

  private String currency;

  private BigDecimal balance;

  private String extra;

  private Long createdAt;

  private Long updatedAt;

  private Long version;

  private Integer state;
}
