package com.blen.exam.controller;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.blen.exam.data.req.WithdrawalCreateParam;
import com.blen.exam.data.req.WithdrawalFetchParam;
import com.blen.exam.data.req.WithdrawalPayParam;
import com.blen.exam.domain.Withdrawal;
import com.blen.exam.service.WithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "提现")
@RequestMapping("/exam/withdrawal")
@RestController
@Validated
public class WithdrawController {

  @Autowired private WithdrawService withdrawService;

  @ApiOperation("创建提现单")
  @PostMapping("/create")
  public Long create(@Validated @RequestBody WithdrawalCreateParam createParam) {
    return withdrawService.create(createParam);
  }

  @ApiOperation("钱包提款")
  @PostMapping("/fetch")
  public void fetch(@Validated @RequestBody WithdrawalFetchParam fetchParam) {
    withdrawService.fetch(fetchParam);
  }

  @ApiOperation("打款")
  @PostMapping("/pay")
  public void pay(@Validated @RequestBody WithdrawalPayParam payParam) {
    withdrawService.pay(payParam);
  }
}
