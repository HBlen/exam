package com.blen.exam.dao;

import com.blen.exam.domain.UserWithdrawAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@Mapper
public interface UserWithdrawAddressMapper {

  UserWithdrawAddress selectByUserIdAndAddress(
      @Param("userId") Long userId,
      @Param("address") String address
  );
}
