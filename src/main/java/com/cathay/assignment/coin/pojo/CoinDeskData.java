package com.cathay.assignment.coin.pojo;

import lombok.Data;

@Data
public class CoinDeskData {

    CoinDeskTime time;

    String disclaimer;

    String chartName;

    CoinDeskBpi bpi;

}
