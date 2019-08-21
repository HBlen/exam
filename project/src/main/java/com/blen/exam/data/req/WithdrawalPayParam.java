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
@ApiModel("打款参数")
public class WithdrawalPayParam {

  @NotNull
  @Min(value = 0, message = "INVALID_ID")
  @Max(value = Long.MAX_VALUE, message = "INVALID_ID")
  @ApiModelProperty(value = "提现单id", required = true)
  private Long id;

  @NotEmpty
  @Length(max = 50)
  @ApiModelProperty(value = "txhash", required = true)
  private String txhash;

  @NotNull
  @Min(value = 0, message = "INVALID_HEIGHT")
  @Max(value = Long.MAX_VALUE, message = "INVALID_HEIGHT")
  @ApiModelProperty(value = "高度", required = true)
  private Long height;

  @NotNull
  @DecimalMin(value = "0.0000000001", message = "INVALID_REAL_FEE")
  @DecimalMax(value = "99999999999999999.9999999999", message = "INVALID_REAL_FEE")
  @ApiModelProperty(value = "实际手续费", required = true)
  private BigDecimal realFee;
}
