package com.cathay.assignment.currency.pojo;

import com.cathay.assignment.currency.dto.CurrencyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomCoinDeskData {

  private String code;

  private String chineseName;

  private BigDecimal rate;

  public CustomCoinDeskData(CurrencyDTO dto) {
    this.code = dto.getCode();
    this.chineseName = dto.getChineseName();
    this.rate = dto.getRate();
  }
}
