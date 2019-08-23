package com.blen.exam.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {

  private Long id;

  private String userName;

  private String phone;

  private String extra;

  private Long createdAt;

  private Long updatedAt;

  private Long version;

  private Integer state;
}
