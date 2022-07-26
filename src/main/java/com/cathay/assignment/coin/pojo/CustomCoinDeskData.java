package com.cathay.assignment.coin.pojo;

import com.cathay.assignment.coin.dto.CoinDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomCoinDeskData {

    private String code;

    private String chineseName;

    private BigDecimal rate;

    public CustomCoinDeskData(CoinDTO dto){
        this.code = dto.getCode();
        this.chineseName = dto.getChineseName();
        this.rate = dto.getRate();
    }
}
