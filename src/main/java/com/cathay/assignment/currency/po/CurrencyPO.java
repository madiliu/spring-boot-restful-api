package com.cathay.assignment.currency.po;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coin")
public class CurrencyPO implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "chinese_name")
  private String chineseName;

  @Column(name = "symbol")
  private String symbol;

  @Column(name = "rate", precision = 15, scale = 5)
  private BigDecimal rate;

  @Column(name = "description")
  private String description;

  @Column(name = "create_time")
  private LocalDateTime createTime;

  @Column(name = "update_time")
  private LocalDateTime updateTime;
}
