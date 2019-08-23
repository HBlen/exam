package com.blen.exam.dao;

import com.blen.exam.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
  long insert(UserInfo userInfo);
}
