package com.blen.exam.dao;

import java.math.BigDecimal;

import com.blen.exam.domain.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserAccountMapper {

  long insert(UserAccount userAccount);
  /**
   * 根据用户id和币种查询
   *
   * @param userID 用户id
   * @param currency 币种
   * @return 账户相关信息
   */
  UserAccount selectByUserIdAndCurrency(
      @Param("userId") Long userID,
      @Param("currency") String currency);

  Integer updateByUserIdAndCurrency(
      @Param("userId") Long userID,
      @Param("currency") String currency,
      @Param("balance") BigDecimal balance);
}
