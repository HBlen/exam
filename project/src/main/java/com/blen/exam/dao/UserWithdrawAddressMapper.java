package com.blen.exam.dao;

import com.blen.exam.domain.UserWithdrawAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserWithdrawAddressMapper {
  long insert(UserWithdrawAddress userWithdrawAddress);
  UserWithdrawAddress selectByUserIdAndAddress(
      @Param("userId") Long userId, @Param("address") String address);
}
