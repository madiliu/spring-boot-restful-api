package com.cathay.assignment.currency.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskBpi {

  @JsonProperty("USD")
  CoinDeskCurrency USD;

  @JsonProperty("GBP")
  CoinDeskCurrency GBP;

  @JsonProperty("EUR")
  CoinDeskCurrency EUR;
}
