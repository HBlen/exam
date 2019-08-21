package com.blen.exam.data.req;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("发起提现参数")
public class WithdrawalCreateParam {

  @NotNull
  @Min(value = 0, message = "INVALID_USER_ID")
  @Max(value = Long.MAX_VALUE, message = "INVALID_USER_ID")
  @ApiModelProperty(value = "用户id", required = true)
  private Long userId;

  @NotEmpty
  @Length(max = 20)
  @ApiModelProperty(value = "币种", required = true)
  private String currency;

  @NotNull
  @DecimalMin(value = "0.0000000001", message = "INVALID_AMOUNT")
  @DecimalMax(value = "99999999999999999.9999999999", message = "INVALID_AMOUNT")
  @ApiModelProperty(value = "金额", required = true)
  private BigDecimal amount;

  @NotNull
  @DecimalMin(value = "0.0000000001", message = "INVALID_FEE")
  @DecimalMax(value = "99999999999999999.9999999999", message = "INVALID_FEE")
  @ApiModelProperty(value = "手续费", required = true)
  private BigDecimal fee;

  @NotEmpty
  @Length(max = 50)
  @ApiModelProperty(value = "提现地址", required = true)
  private String address;
}
