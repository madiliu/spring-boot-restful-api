package com.cathay.assignment.currency.service;

import com.cathay.assignment.currency.dto.CoinDeskDTO;
import com.cathay.assignment.currency.dto.CurrencyDTO;
import com.cathay.assignment.currency.pojo.CoinDeskData;
import com.cathay.assignment.currency.vo.InsertCurrencyCondition;
import com.cathay.assignment.currency.vo.UpdateCurrencyCondition;

import java.text.ParseException;
import java.util.List;

public interface CurrencyService {

  CoinDeskData getCoinDeskData();

  CoinDeskDTO convertCoinDeskData() throws ParseException;

  List<CurrencyDTO> insertCoinDeskData();

  CurrencyDTO findByCode(String code) throws Exception;

  CurrencyDTO insert(InsertCurrencyCondition condition) throws Exception;

  CurrencyDTO update(String code, UpdateCurrencyCondition condition) throws Exception;

  void delete(String code) throws Exception;
}
