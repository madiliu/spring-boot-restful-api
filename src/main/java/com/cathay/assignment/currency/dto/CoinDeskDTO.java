package com.cathay.assignment.currency.dto;

import com.cathay.assignment.currency.pojo.CustomCoinDeskData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskDTO {

  private Map<String, String> updateTime;

  private List<CustomCoinDeskData> coinInfo;
}
