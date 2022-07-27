package com.cathay.assignment.currency.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCurrencyCondition {

  private String symbol;

  private String chineseName;

  private BigDecimal rate;

  private String description;
}
