package com.blen.exam.controller;

import com.blen.exam.data.req.WithdrawalCreateParam;
import com.blen.exam.data.req.WithdrawalFetchParam;
import com.blen.exam.data.req.WithdrawalPayParam;
import com.blen.exam.service.WithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public Integer fetch(@Validated @RequestBody WithdrawalFetchParam fetchParam) {
    return withdrawService.fetch(fetchParam);
  }

  @ApiOperation("打款")
  @PostMapping("/pay")
  public Integer pay(@Validated @RequestBody WithdrawalPayParam payParam) {
    return withdrawService.pay(payParam);
  }
}
