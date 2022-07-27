package com.cathay.assignment.currency.vo;

import com.cathay.assignment.currency.pojo.CoinDeskCurrency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsertCurrencyCondition {

  @NotEmpty(message = "code cannot be blank")
  private String code;

  @NotEmpty(message = "chinese name cannot be blank")
  private String chineseName;

  @NotEmpty(message = "symbol cannot be blank")
  private String symbol;

  @NotNull(message = "rate cannot be null")
  private BigDecimal rate;

  private String description;

  public InsertCurrencyCondition(CoinDeskCurrency currency, String chineseName) {
    this.code = currency.getCode().toString();
    this.chineseName = chineseName;
    this.symbol = currency.getSymbol();
    this.rate = currency.getRateFloat();
    this.description = currency.getDescription();
  }
}
