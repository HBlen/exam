package com.blen.exam.dao;

import java.math.BigDecimal;
import java.util.List;

import com.blen.exam.domain.Withdrawal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WithdrawalMapper {

  long insert(Withdrawal withdrawal);

  /**
   * 根据用户id,更新时间和状态查询提现单，用于查看一天的提现额是否超标
   * @param userID 用户id
   * @param state 状态， 0已废弃 1初始创建  2钱包提走 3已打款
   * @return 提现单信息
   */
  List<Withdrawal> selectByUserIdAndUpdateAt(
      @Param("userId") Long userID,
      @Param("currency") String currency,
      @Param("todayStart") Long todayStart,
      @Param("todayEnd") Long todayEnd,
      @Param("state") Integer state
  );

  /**
   * 根据订单id和状态查询提现单
   * @param id 提现单id
   * @param state 状态， 0已废弃 1初始创建  2钱包提走 3已打款
   * @return
   */
  Withdrawal selectByIdAndState(
    @Param("id") Long id,
    @Param("state") Integer state
  );

  /**
   * 更改提现单状态
   * @param id 提现单id
   * @param state 状态， 0已废弃 1初始创建  2钱包提走 3已打款
   * @return
   */
  Integer updateStateById(
      @Param("id") Long id,
      @Param("state") Integer state
  );

  /**
   *  修改打款相关信息
   * @param id 提现单id
   * @param txhash  txhash
   * @param height 高度
   * @param realFee 实际手续费
   * @return
   */
  Integer updateTxhashHeightAndRealFee(
      @Param("id") Long id,
      @Param("txhash") String txhash,
      @Param("height") Long height,
      @Param("realFee") BigDecimal realFee
  );
}
