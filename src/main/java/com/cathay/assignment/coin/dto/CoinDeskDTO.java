package com.cathay.assignment.coin.dto;

import com.cathay.assignment.coin.pojo.CustomCoinDeskData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class CoinDeskDTO {

    private Map<String, String> updateTime;

    private List<CustomCoinDeskData> coinInfo;

}
