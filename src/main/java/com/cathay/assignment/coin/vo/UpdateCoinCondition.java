package com.cathay.assignment.coin.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateCoinCondition {

  private String symbol;

  private String chineseName;

  private BigDecimal rate;

  private String description;
}
