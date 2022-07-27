package com.cathay.assignment.currency.controller;

import com.cathay.assignment.currency.common.RestControllerAbstract;
import com.cathay.assignment.currency.common.RestResults;
import com.cathay.assignment.currency.dto.CoinDeskDTO;
import com.cathay.assignment.currency.dto.CurrencyDTO;
import com.cathay.assignment.currency.pojo.CoinDeskData;
import com.cathay.assignment.currency.service.CurrencyService;
import com.cathay.assignment.currency.vo.InsertCurrencyCondition;
import com.cathay.assignment.currency.vo.UpdateCurrencyCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/currency")
public class CurrencyController extends RestControllerAbstract {

  private final CurrencyService service;

  /**
   * 呼叫 coindesk api，回傳原始幣別資訊
   *
   * @return CoinDeskData
   */
  @GetMapping(value = "/coinDesk/get")
  public ResponseEntity<RestResults<CoinDeskData>> getCoinDeskData() {
    log.info("Start getCoinDeskData");
    try {
      return success(service.getCoinDeskData());
    } catch (Exception e) {
      log.error(("Call failed, error details: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to call the coindesk api, " + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * 呼叫 coindesk api 並將其資料寫入 DB，回傳新增內容（一次性使用）
   *
   * @return List<CurrencyDTO>
   */
  @PostMapping(value = "/coinDesk/insert")
  public ResponseEntity<RestResults<List<CurrencyDTO>>> insertCoinDeskDate() {
    log.info("Start insertCoinDeskApi");
    try {
      return success(service.insertCoinDeskData());
    } catch (RuntimeException e) {
      log.error(("Insert failed with RuntimeException, error message: {}"), e.getMessage());
      return fail(HttpStatus.BAD_REQUEST, e.getMessage() + ", please provide the valid input");
    } catch (Exception e) {
      log.error(("Insert failed, error details: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to insert the coindesk data, error details: "
              + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * 呼叫 coindesk api，進行資料轉換，回傳新組成的幣別資訊
   *
   * @return CoinDeskDTO
   */
  @GetMapping(value = "/coinDesk/convert")
  public ResponseEntity<RestResults<CoinDeskDTO>> convertCoinDeskData() {
    log.info("Start convertCoinDeskData");
    try {
      return success(service.convertCoinDeskData());
    } catch (ParseException e) {
      log.error(("Convert failed with ParseException, error message: {}"), e.getMessage());
      return fail(HttpStatus.BAD_REQUEST, e.getMessage() + ", please insert the data firstly");
    } catch (Exception e) {
      log.error(("Convert failed, error details: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to convert the coindesk data, error details: "
              + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * 查詢：輸入幣別代碼，回傳 DB 中的幣別資訊
   *
   * @param code
   * @return CurrencyDTO
   */
  @GetMapping(value = "/{code}")
  public ResponseEntity<RestResults<CurrencyDTO>> findByCode(@PathVariable String code) {
    log.info(("Start findByCode with code {}"), code);
    try {
      return success(service.findByCode(code));
    } catch (RuntimeException e) {
      log.error(("Find failed with RuntimeException, error message: {}"), e.getMessage());
      return fail(HttpStatus.NOT_FOUND, e.getMessage() + ", please provide the valid code");
    } catch (Exception e) {
      log.error(("Find failed, error details: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to find the currency, error details " + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * 新增：輸入單一幣別資訊以新增資料至 DB，回傳 DB 中新增的幣別資訊
   *
   * @param condition
   * @return CurrencyDTO
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResults<CurrencyDTO>> insert(
      @RequestBody @Valid InsertCurrencyCondition condition) {
    log.info(("Start insert with condition {}"), condition.toString());
    try {
      return success(service.insert(condition));
    } catch (RuntimeException e) {
      log.error(("Insert failed with RuntimeException, error message: {}"), e.getMessage());
      return fail(HttpStatus.BAD_REQUEST, e.getMessage() + ", please provide the valid input");
    } catch (Exception e) {
      log.error(("Insert failed, error details: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to insert the currency, error details: " + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * 更新：輸入欲更新的單一幣別資訊以修改 DB，回傳 DB 中更新後的幣別資訊
   *
   * @param code
   * @param condition
   * @return CurrencyDTO
   */
  @PutMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResults<CurrencyDTO>> update(
      @PathVariable String code, @RequestBody @Valid UpdateCurrencyCondition condition) {
    log.info(("Start update with code {} and condition {}"), code, condition.toString());
    try {
      return success(service.update(code, condition));
    } catch (RuntimeException e) {
      log.error(("Update failed with RuntimeException, error message: {}"), e.getMessage());
      return fail(HttpStatus.BAD_REQUEST, e.getMessage() + ", please provide the valid code");
    } catch (Exception e) {
      log.error(("Update failed, error details: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to update the currency, error details: " + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * 刪除：輸入幣別代碼以刪除 DB 中的幣別資訊
   *
   * @param code
   */
  @DeleteMapping(value = "/{code}")
  public ResponseEntity<RestResults<String>> delete(@PathVariable String code) {
    log.info(("Start delete with code {}"), code);
    try {
      service.delete(code);
      return success("Deleted successfully");
    } catch (RuntimeException e) {
      log.error(("Delete failed with RuntimeException, error message: {}"), e.getMessage());
      return fail(HttpStatus.BAD_REQUEST, e.getMessage() + ", please provide the valid code");
    } catch (Exception e) {
      log.error(("Delete failed, error details: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to delete the currency, error details: " + Arrays.toString(e.getStackTrace()));
    }
  }
}
