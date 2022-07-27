package com.cathay.assignment.currency.service;

import com.cathay.assignment.currency.api.CoinDeskApi;
import com.cathay.assignment.currency.dto.CoinDeskDTO;
import com.cathay.assignment.currency.dto.CurrencyDTO;
import com.cathay.assignment.currency.po.CurrencyPO;
import com.cathay.assignment.currency.pojo.CoinDeskBpi;
import com.cathay.assignment.currency.pojo.CoinDeskData;
import com.cathay.assignment.currency.pojo.CoinDeskTime;
import com.cathay.assignment.currency.pojo.CustomCoinDeskData;
import com.cathay.assignment.currency.repository.CurrencyDepository;
import com.cathay.assignment.currency.vo.InsertCurrencyCondition;
import com.cathay.assignment.currency.vo.UpdateCurrencyCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

  private final CurrencyDepository depository;

  private final CoinDeskApi api;

  private static final SimpleDateFormat UTC_DATE_FORMAT =
      new SimpleDateFormat(
          "MMM d, yyyy HH:mm:ss", Locale.US); // updated: "Jul 26, 2022 09:34:00 UTC",
  private static final SimpleDateFormat ISO_DATE_FORMAT =
      new SimpleDateFormat(
          "yyyy-MM-dd'T'HH:mm:ssXXX", Locale.UK); // updatedISO: "2022-07-26T09:34:00+00:00",
  private static final SimpleDateFormat BST_DATE_FORMAT =
      new SimpleDateFormat(
          "MMM d, yyyy 'at' HH:mm", Locale.UK); // updateduk: "Jul 26, 2022 at 10:34 BST"
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

  /**
   * 呼叫 coindesk api，回傳原始幣別資訊
   *
   * @return CoinDeskData
   */
  @Override
  public CoinDeskData getCoinDeskData() {
    return api.getCoinDeskData();
  }

  /**
   * 呼叫 coindesk api 並將其資料寫入 DB，回傳新增內容（一次性使用）
   *
   * @return List<CurrencyDTO>
   */
  @Override
  public List<CurrencyDTO> insertCoinDeskData() {
    CoinDeskData coinDeskData = getCoinDeskData();
    CoinDeskBpi bpi = coinDeskData.getBpi();
    CurrencyDTO usdDto = insert(new InsertCurrencyCondition(bpi.getUSD(), "美元"));
    CurrencyDTO eurDto = insert(new InsertCurrencyCondition(bpi.getEUR(), "歐元"));
    CurrencyDTO gbpDto = insert(new InsertCurrencyCondition(bpi.getGBP(), "英鎊"));
    return new ArrayList<>(Arrays.asList(usdDto, eurDto, gbpDto));
  }

  /**
   * 呼叫 coindesk api，進行資料轉換，回傳新組成的幣別資訊
   *
   * @return CoinDeskDTO
   * @throws ParseException
   */
  @Override
  public CoinDeskDTO convertCoinDeskData() throws ParseException {
    CoinDeskData rawData = getCoinDeskData();
    CoinDeskTime rawUpdateTime = rawData.getTime();
    CoinDeskBpi bpi = rawData.getBpi();
    String formatUpdateUTC = formatDate(rawUpdateTime.getUpdated(), UTC_DATE_FORMAT, "UTC");
    String formatUpdateISO = formatDate(rawUpdateTime.getUpdatedISO(), ISO_DATE_FORMAT, "");
    String formatUpdateBST = formatDate(rawUpdateTime.getUpdateduk(), BST_DATE_FORMAT, "BST");
    Map<String, String> updateTime =
        new HashMap<String, String>() {
          {
            put("UTC: ", formatUpdateUTC);
            put("ISO: ", formatUpdateISO);
            put("BST: ", formatUpdateBST);
          }
        };
    CustomCoinDeskData USD = new CustomCoinDeskData(findByCode("USD"));
    CustomCoinDeskData GBP = new CustomCoinDeskData(findByCode("GBP"));
    CustomCoinDeskData EUR = new CustomCoinDeskData(findByCode("EUR"));
    return CoinDeskDTO.builder()
        .updateTime(updateTime)
        .coinInfo(Arrays.asList(USD, GBP, EUR))
        .build();
  }

  /**
   * 查詢：輸入幣別代碼，回傳 DB 中的幣別資訊
   *
   * @param code
   * @return CurrencyDTO
   */
  @Override
  public CurrencyDTO findByCode(String code) {
    CurrencyPO po =
        depository.findByCode(code).orElseThrow(() -> new RuntimeException("Currency does not exist"));
    return CurrencyDTO.builder()
        .code(po.getCode())
        .chineseName(po.getChineseName())
        .symbol(po.getSymbol())
        .rate(po.getRate())
        .description(po.getDescription())
        .createTime(po.getCreateTime())
        .updateTime(po.getUpdateTime())
        .build();
  }

  /**
   * 新增：輸入單一幣別資訊以新增資料至 DB，回傳 DB 中新增的幣別資訊
   *
   * @param condition
   * @return CurrencyDTO
   */
  @Override
  public CurrencyDTO insert(InsertCurrencyCondition condition) {
    CurrencyPO po =
        CurrencyPO.builder()
            .code(condition.getCode())
            .chineseName(condition.getChineseName())
            .symbol(condition.getSymbol())
            .rate(condition.getRate())
            .description(condition.getDescription())
            .createTime(LocalDateTime.now())
            .build();
    if (!depository.findByCode(condition.getCode()).isPresent()) {
      depository.save(po);
      return CurrencyDTO.builder()
          .code(po.getCode())
          .chineseName(po.getChineseName())
          .symbol(po.getSymbol())
          .rate(po.getRate())
          .description(po.getDescription())
          .createTime(LocalDateTime.now())
          .build();
    } else {
      throw new RuntimeException("Currency already exists");
    }
  }

  /**
   * 更新：輸入欲更新的單一幣別資訊以修改 DB，回傳 DB 中更新後的幣別資訊
   *
   * @param code
   * @param condition
   * @return CurrencyDTO
   */
  @Override
  public CurrencyDTO update(String code, UpdateCurrencyCondition condition) {

    CurrencyPO po =
        depository.findByCode(code).orElseThrow(() -> new RuntimeException("Currency does not exist"));
    if (condition.getSymbol() != null) {
      po.setSymbol(condition.getSymbol());
    }
    if (condition.getChineseName() != null) {
      po.setChineseName(condition.getChineseName());
    }
    if (condition.getSymbol() != null) {
      po.setSymbol(condition.getSymbol());
    }
    if (condition.getRate() != null) {
      po.setRate(condition.getRate());
    }
    if (condition.getDescription() != null) {
      po.setDescription(condition.getDescription());
    }
    po.setUpdateTime(LocalDateTime.now());
    depository.save(po);
    return CurrencyDTO.builder()
        .code(po.getCode())
        .chineseName(po.getChineseName())
        .symbol(po.getSymbol())
        .rate(po.getRate())
        .description(po.getDescription())
        .createTime(po.getCreateTime())
        .updateTime(po.getUpdateTime())
        .build();
  }

  /**
   * 刪除：輸入幣別代碼以刪除 DB 中的幣別資訊
   *
   * @param code
   */
  @Override
  public void delete(String code) {
    Optional<CurrencyPO> po = depository.findByCode(code);
    if (po.isPresent()) {
      depository.deleteById(po.get().getId());
    } else {
      throw new RuntimeException("Currency does not exist");
    }
  }

  /**
   * 轉換時間格式
   *
   * @param rawDate
   * @param formatter
   * @param zone
   * @return String
   * @throws ParseException
   */
  private String formatDate(String rawDate, SimpleDateFormat formatter, String zone)
      throws ParseException {
    rawDate = rawDate.replace(zone, "").trim();
    Date parseDate = formatter.parse(rawDate);
    return DATE_FORMAT.format(parseDate);
  }
}
