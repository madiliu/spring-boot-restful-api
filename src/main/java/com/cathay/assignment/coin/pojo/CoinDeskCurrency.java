package com.cathay.assignment.coin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
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
