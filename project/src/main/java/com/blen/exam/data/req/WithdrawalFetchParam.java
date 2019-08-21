package com.blen.exam.data.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("钱包打款参数")
public class WithdrawalFetchParam {

  @NotNull
  @Min(value = 0, message = "INVALID_USER_ID")
  @Max(value = Long.MAX_VALUE, message = "INVALID_USER_ID")
  @ApiModelProperty(value = "用户id", required = true)
  private Long userId;

  @NotNull
  @Min(value = 0, message = "INVALID_ID")
  @Max(value = Long.MAX_VALUE, message = "INVALID_ID")
  @ApiModelProperty(value = "提现单id", required = true)
  private Long id;
}
