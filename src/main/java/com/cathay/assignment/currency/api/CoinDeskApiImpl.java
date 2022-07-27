package com.cathay.assignment.currency.api;

import com.cathay.assignment.currency.common.CoinDeskRestTemplate;
import com.cathay.assignment.currency.pojo.CoinDeskData;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CoinDeskApiImpl implements CoinDeskApi {

    private final String COIN_DESK_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Override
    public CoinDeskData getCoinDeskData() {
        RestTemplate restTemplate = new CoinDeskRestTemplate().getTemplate();
        return restTemplate.getForObject(COIN_DESK_URL, CoinDeskData.class);
    }
}
