package com.cathay.assignment.currency.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskCurrency {

  public enum CurrencyCode {
    USD,
    GBP,
    EUR
  }

  public CurrencyCode code;

  private String symbol;

  private String rate;

  private String description;

  @JsonProperty("rate_float")
  private BigDecimal rateFloat;
}
