package com.cathay.assignment.coin.service;

import com.cathay.assignment.coin.dto.CoinDeskDTO;
import com.cathay.assignment.coin.dto.CoinDTO;
import com.cathay.assignment.coin.pojo.CoinDeskData;
import com.cathay.assignment.coin.vo.InsertCoinCondition;
import com.cathay.assignment.coin.vo.UpdateCoinCondition;

import java.text.ParseException;
import java.util.List;

public interface CoinService {

  CoinDeskData getCoinDeskData();

  CoinDeskDTO convertCoinDeskData() throws ParseException;

  List<CoinDTO> insertCoinDeskData();

  CoinDTO findByCode(String code) throws Exception;

  CoinDTO insert(InsertCoinCondition condition) throws Exception;

  CoinDTO update(String code, UpdateCoinCondition condition) throws Exception;

  void delete(String code) throws Exception;
}
