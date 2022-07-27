package com.cathay.assignment.currency.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskTime {

  private String updated;

  private String updatedISO;

  private String updateduk;
}
