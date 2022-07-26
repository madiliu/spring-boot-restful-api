package com.cathay.assignment.coin.api;

import com.cathay.assignment.coin.pojo.CoinDeskData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.cathay.assignment.coin.common.CoinDeskRestTemplate;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class CoinDeskApiImpl implements CoinDeskApi {

  private static final String URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

  @Override
  public CoinDeskData getCoinDeskData() {
    RestTemplate template = new CoinDeskRestTemplate().getTemplate();
    return template.getForEntity(URL, CoinDeskData.class).getBody();
  }
}
