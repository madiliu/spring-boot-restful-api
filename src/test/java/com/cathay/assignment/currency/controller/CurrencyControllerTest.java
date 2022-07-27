package com.cathay.assignment.currency.controller;

import com.cathay.assignment.currency.common.RestResults;
import com.cathay.assignment.currency.dto.CoinDeskDTO;
import com.cathay.assignment.currency.dto.CurrencyDTO;
import com.cathay.assignment.currency.pojo.CoinDeskData;
import com.cathay.assignment.currency.pojo.CoinDeskTime;
import com.cathay.assignment.currency.pojo.CustomCoinDeskData;
import com.cathay.assignment.currency.service.CurrencyService;
import com.cathay.assignment.currency.vo.InsertCurrencyCondition;
import com.cathay.assignment.currency.vo.UpdateCurrencyCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CurrencyController.class)
@DisplayName("CurrencyControllerTest")
public class CurrencyControllerTest {

  @SpyBean CurrencyController controller;

  @MockBean CurrencyService service;

  @Test
  @DisplayName("getCoinDeskDataTest")
  void getCoinDeskDataTest() {

    when(service.getCoinDeskData()).thenReturn(createCoinDeskData());

    ResponseEntity<RestResults<CoinDeskData>> result = controller.getCoinDeskData();

    assertThat(result)
        .extracting(ResponseEntity::getBody)
        .extracting(RestResults::getData)
        .extracting(CoinDeskData::getTime)
        .extracting("updated", "updatedISO")
        .contains("Jul 26, 2022 09:34:00 UTC", "2022-07-26T09:34:00+00:00");
  }

  @Test
  @DisplayName("insertCoinDeskDataTest")
  void insertCoinDeskDataTest() {

    when(service.insertCoinDeskData())
        .thenReturn(Arrays.asList(createCurrencyA(), createCurrencyB()));

    ResponseEntity<RestResults<List<CurrencyDTO>>> result = controller.insertCoinDeskDate();

    assertThat(result)
        .extracting(ResponseEntity::getBody)
        .extracting(RestResults::getData)
        .asList()
        .hasSize(2)
        .extracting("code", "rate", "description")
        .contains(
            tuple("JPY", BigDecimal.valueOf(40.00), "Japanese Yen"),
            tuple("USD", BigDecimal.valueOf(23.52), "United States Dollar"));
  }

  @Test
  @DisplayName("convertCoinDeskDataTest")
  void convertCoinDeskDataTest() throws ParseException {

    when(service.convertCoinDeskData()).thenReturn(createCoinDeskDTO());

    ResponseEntity<RestResults<CoinDeskDTO>> result = controller.convertCoinDeskData();

    assertThat(result)
        .extracting(ResponseEntity::getBody)
        .extracting(RestResults::getData)
        .extracting(CoinDeskDTO::getUpdateTime)
        .extracting("updated", "updatedISO")
        .contains("Jul 26, 2022 09:34:00 UTC", "2022-07-26T09:34:00+00:00");
  }

  @Test
  @DisplayName("findByCode")
  void findByCode() throws Exception {

    when(service.findByCode("JPY")).thenReturn(createCurrencyA());

    ResponseEntity<RestResults<CurrencyDTO>> result = controller.findByCode("JPY");

    assertThat(result)
        .extracting(ResponseEntity::getBody)
        .extracting(RestResults::getData)
        .extracting("symbol", "description")
        .contains("¥", "Japanese Yen");
  }

  @Test
  @DisplayName("insert")
  void insert() throws Exception {

    InsertCurrencyCondition condition =
        InsertCurrencyCondition.builder()
            .code("USD")
            .chineseName("美元")
            .symbol("$")
            .rate(BigDecimal.valueOf(23.52))
            .build();

    when(service.insert(condition)).thenReturn(createCurrencyB());

    ResponseEntity<RestResults<CurrencyDTO>> result = controller.insert(condition);

    assertThat(result)
        .extracting(ResponseEntity::getBody)
        .extracting(RestResults::getData)
        .extracting("symbol", "description")
        .contains("$", "United States Dollar");
  }

  @Test
  @DisplayName("update")
  void update() throws Exception {

    UpdateCurrencyCondition condition =
        UpdateCurrencyCondition.builder().chineseName("測試").rate(BigDecimal.valueOf(0.00)).build();

    CurrencyDTO currency = createCurrencyB();
    currency.setChineseName("測試");
    currency.setRate(BigDecimal.valueOf(0.00));

    when(service.update("USD", condition)).thenReturn(currency);

    ResponseEntity<RestResults<CurrencyDTO>> result = controller.update("USD", condition);

    assertThat(result)
        .extracting(ResponseEntity::getBody)
        .extracting(RestResults::getData)
        .extracting("symbol", "rate", "chineseName", "description")
        .contains("$", BigDecimal.valueOf(0.00), "測試", "United States Dollar");
  }

  @Test
  @DisplayName("delete")
  void delete() throws Exception {

    String message = "Deleted successfully";

    ResponseEntity<RestResults<String>> result = controller.delete("JPY");

    CurrencyDTO currency = service.findByCode("JPY");

    assertThat(currency).isNull();

    assertThat(result)
        .extracting(ResponseEntity::getBody)
        .extracting(RestResults::getData)
        .isEqualTo(message);
  }

  private CoinDeskData createCoinDeskData() {
    CoinDeskTime time =
        CoinDeskTime.builder()
            .updated("Jul 26, 2022 09:34:00 UTC")
            .updatedISO("2022-07-26T09:34:00+00:00")
            .build();
    return CoinDeskData.builder().time(time).build();
  }

  private CurrencyDTO createCurrencyA() {
    return CurrencyDTO.builder()
        .code("JPY")
        .chineseName("日幣")
        .symbol("¥")
        .rate(BigDecimal.valueOf(40.00))
        .description("Japanese Yen")
        .build();
  }

  private CurrencyDTO createCurrencyB() {
    return CurrencyDTO.builder()
        .code("USD")
        .chineseName("美元")
        .symbol("$")
        .rate(BigDecimal.valueOf(23.52))
        .description("United States Dollar")
        .build();
  }

  private CoinDeskDTO createCoinDeskDTO() {
    CustomCoinDeskData currencyA = new CustomCoinDeskData(createCurrencyA());
    CustomCoinDeskData currencyB = new CustomCoinDeskData(createCurrencyB());
    Map<String, String> updateTime = new HashMap<>();
    updateTime.put("updated", "Jul 26, 2022 09:34:00 UTC");
    updateTime.put("updatedISO", "2022-07-26T09:34:00+00:00");
    return CoinDeskDTO.builder()
        .updateTime(updateTime)
        .coinInfo(Arrays.asList(currencyA, currencyB))
        .build();
  }
}
