package com.cathay.assignment.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {

  private String code;

  private String chineseName;

  private String symbol;

  private BigDecimal rate;

  private String description;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;
}
