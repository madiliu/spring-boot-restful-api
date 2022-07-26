package com.cathay.assignment.coin.controller;

import com.cathay.assignment.coin.common.RestControllerAbstract;
import com.cathay.assignment.coin.common.RestResults;
import com.cathay.assignment.coin.dto.CoinDeskDTO;
import com.cathay.assignment.coin.dto.CoinDTO;
import com.cathay.assignment.coin.pojo.CoinDeskData;
import com.cathay.assignment.coin.service.CoinService;
import com.cathay.assignment.coin.vo.InsertCoinCondition;
import com.cathay.assignment.coin.vo.UpdateCoinCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/coin")
public class CoinController extends RestControllerAbstract {

  private final CoinService coinService;

  /**
   * 呼叫 coindesk api，回傳原始幣別資訊
   *
   * @return CoinDeskData
   */
  @GetMapping(value = "/coinDesk")
  public ResponseEntity<RestResults<CoinDeskData>> getCoinDeskData() {
    log.info("Start getCoinDeskData");
    try {
      return success(coinService.getCoinDeskData());
    } catch (Exception e) {
      log.error(("Call failed, error message: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to call the coindesk api, " + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * 呼叫 coindesk api，進行資料轉換，回傳新組成的幣別資訊
   *
   * @return CustomCoinDeskData
   */
  @GetMapping(value = "/convertCoinDesk")
  public ResponseEntity<RestResults<CoinDeskDTO>> convertCoinDeskData() {
    log.info("Start convertCoinDeskData");
    try {
      return success(coinService.convertCoinDeskData());
    } catch (RuntimeException e){
      log.error(("Convert failed with RuntimeException, error message: {}"), e.getMessage());
      return fail(HttpStatus.BAD_REQUEST, e.getMessage() + ", please insert the data firstly");
    } catch (Exception e) {
      log.error(("Convert failed, error message: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to convert the coindesk api, error message: "
              + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * 呼叫 coindesk api 並將其資料寫入 DB，回傳新增內容（一次性使用）
   *
   * @return List<CoinDTO>
   */
  @PostMapping(value = "/insertCoinDesk")
  public ResponseEntity<RestResults<List<CoinDTO>>> insertCoinDeskDate() {
    log.info("Start insertCoinDeskApi");
    try {
      return success(coinService.insertCoinDeskData());
    } catch (RuntimeException e) {
      log.error(("Insert failed with RuntimeException, error message: {}"), e.getMessage());
      return fail(HttpStatus.BAD_REQUEST, e.getMessage() + ", please provide the valid input");
    } catch (Exception e) {
      log.error(("Insert failed, error message: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to insert the coin, error message: " + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * 查詢：輸入幣別代碼，回傳 DB 中的幣別資訊
   *
   * @param code
   * @return CoinDTO
   */
  @GetMapping(value = "/{code}")
  public ResponseEntity<RestResults<CoinDTO>> findByCode(@PathVariable String code) {
    log.info(("Start findByCode with code {}"), code);
    try {
      return success(coinService.findByCode(code));
    } catch (RuntimeException e) {
      log.error(("Find failed with RuntimeException, error message: {}"), e.getMessage());
      return fail(HttpStatus.NOT_FOUND, e.getMessage() + ", please provide the valid code");
    } catch (Exception e) {
      log.error(("Find failed, error message: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to find the coin, error message: " + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * 新增：輸入單一幣別資訊以新增資料至 DB，回傳 DB 中新增的幣別資訊
   *
   * @param condition
   * @return CoinDTO
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResults<CoinDTO>> insert(
      @RequestBody @Valid InsertCoinCondition condition) {
    log.info(("Start insert with condition {}"), condition.toString());
    try {
      return success(coinService.insert(condition));
    } catch (RuntimeException e) {
      log.error(("Insert failed with RuntimeException, error message: {}"), e.getMessage());
      return fail(HttpStatus.BAD_REQUEST, e.getMessage() + ", please provide the valid input");
    } catch (Exception e) {
      log.error(("Insert failed, error message: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to insert the coin, error message: " + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * 更新：輸入欲更新的單一幣別資訊以修改 DB，回傳 DB 中更新後的幣別資訊
   *
   * @param code
   * @param condition
   * @return CoinDTO
   */
  @PutMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResults<CoinDTO>> update(
      @PathVariable String code, @RequestBody @Valid UpdateCoinCondition condition) {
    log.info(("Start update with code {} and condition {}"), code, condition.toString());
    try {
      return success(coinService.update(code, condition));
    } catch (RuntimeException e) {
      log.error(("Update failed with RuntimeException, error message: {}"), e.getMessage());
      return fail(HttpStatus.BAD_REQUEST, e.getMessage() + ", please provide the valid code");
    } catch (Exception e) {
      log.error(("Update failed, error message: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to update the coin, error message: " + Arrays.toString(e.getStackTrace()));
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
      coinService.delete(code);
      return success("Delete successfully");
    } catch (RuntimeException e) {
      log.error(("Delete failed with RuntimeException, error message: {}"), e.getMessage());
      return fail(HttpStatus.BAD_REQUEST, e.getMessage() + ", please provide the valid code");
    } catch (Exception e) {
      log.error(("Delete failed, error message: {}"), Arrays.toString(e.getStackTrace()));
      return fail(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to delete the coin, error message: " + Arrays.toString(e.getStackTrace()));
    }
  }
}
