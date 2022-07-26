package com.cathay.assignment.coin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CoinDTO {

  private String code;

  private String chineseName;

  private String symbol;

  private BigDecimal rate;

  private String description;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;
}
