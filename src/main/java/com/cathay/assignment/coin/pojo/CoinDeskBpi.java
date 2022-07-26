package com.cathay.assignment.coin.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoinDeskBpi {

    @JsonProperty("USD")
    CoinDeskCurrency USD;

    @JsonProperty("GBP")
    CoinDeskCurrency GBP;

    @JsonProperty("EUR")
    CoinDeskCurrency EUR;


}
