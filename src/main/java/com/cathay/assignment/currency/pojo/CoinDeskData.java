package com.cathay.assignment.currency.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskData {

  CoinDeskTime time;

  String disclaimer;

  String chartName;

  CoinDeskBpi bpi;
}
