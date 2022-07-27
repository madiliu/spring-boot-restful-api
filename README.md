# Cathay_Assignment_Currency

## [01] 呼叫 coindesk API
### `GET: /api/currency/coinDesk/getData`
#### Description: 呼叫 coindesk api，回傳原始幣別資訊
#### Parameters: no parameters
#### Response Body
##### 200 OK
```json
{
    "data": {
        "time": {
            "updated": "Jul 27, 2022 16:19:00 UTC",
            "updatedISO": "2022-07-27T16:19:00+00:00",
            "updatedUK": null
        },
        "disclaimer": "This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org",
        "chartName": "Bitcoin",
        "bpi": {
            "USD": {
                "code": "USD",
                "symbol": "&#36;",
                "rate": "21,677.2096",
                "description": "United States Dollar",
                "rate_float": 21677.2096
            },
            "GBP": {
                "code": "GBP",
                "symbol": "&pound;",
                "rate": "18,113.3029",
                "description": "British Pound Sterling",
                "rate_float": 18113.3029
            },
            "EUR": {
                "code": "EUR",
                "symbol": "&euro;",
                "rate": "21,116.7670",
                "description": "Euro",
                "rate_float": 21116.767
            }
        }
    }
}
```

##### 500 Internal Server Error
```json
{
    "data": "Failed to call the coindesk api, error details: ...."
}
```


## [02] 新增 coindesk API 內容
### `POST: /api/currency/coinDesk/insertData`
#### Description: 呼叫 coindesk api 並將其資料寫入 DB，回傳新增內容（一次性使用）
#### Parameters: no parameters
#### Response Body
##### 200 OK
```json
{
    "data": [
        {
            "code": "USD",
            "chineseName": "美元",
            "symbol": "&#36;",
            "rate": 21686.5117,
            "description": "United States Dollar",
            "createTime": "2022-07-28T00:23:53.72",
            "updateTime": null
        },
        {
            "code": "EUR",
            "chineseName": "歐元",
            "symbol": "&euro;",
            "rate": 21125.8287,
            "description": "Euro",
            "createTime": "2022-07-28T00:23:53.723",
            "updateTime": null
        },
        {
            "code": "GBP",
            "chineseName": "英鎊",
            "symbol": "&pound;",
            "rate": 18121.0757,
            "description": "British Pound Sterling",
            "createTime": "2022-07-28T00:23:53.727",
            "updateTime": null
        }
    ]
}
```

##### 400 Bad Request
```json
{
    "data": "Currency already exists, please provide the valid input"
}
```

##### 500 Internal Server Error
```json
{
    "data": "Failed to insert the coindesk data, error details: ...."
}
```

## [03] 資轉 coindesk API 內容
### `GET: /api/currency/coinDesk/convertData`
#### Description: 呼叫 coindesk api，進行資料轉換，回傳新組成的幣別資訊
#### Parameters: no parameters
#### Response Body
##### 200 OK
```json
{
    "data": {
        "updateTime": {
            "ISO: ": "2022/07/28 01:13:00",
            "BST: ": "2022/07/27 18:13:00",
            "UTC: ": "2022/07/27 17:13:00"
        },
        "coinInfo": [
            {
                "code": "USD",
                "chineseName": "美元",
                "rate": 21492.06890
            },
            {
                "code": "GBP",
                "chineseName": "英鎊",
                "rate": 17958.60080
            },
            {
                "code": "EUR",
                "chineseName": "歐元",
                "rate": 20936.41300
            }
        ]
    }
}
```

##### 400 Bad Request
```json
{
    "data": "Coindesk data has not been inserted, please insert the data firstly"
}
```

##### 500 Internal Server Error
```json
{
    "data": "Failed to convert the coindesk api, error details: ...."
}
```

## [04] 查詢
### `GET: /api/currency/{code}`
#### Description: 輸入幣別代碼，回傳 DB 中的幣別資訊
#### Parameters
| No  | Param             | Description               | Data Type           | Required  | Default     | Example         |
|---- |-----------------  |-------------------------  |-------------------- |---------- |------------ |---------------  |
| 1   | code      | 幣別代碼| String               | Y         |             |  USD            |

#### Response Body
##### 200 OK
```json
{
    "data": {
        "code": "美元",
        "chineseName": null,
        "symbol": "&#36;",
        "rate": 21693.09030,
        "description": "United States Dollar",
        "createTime": "2022-07-28T00:32:47.703",
        "updateTime": null
    }
}
```

##### 400 Bad Request
```json
{
    "data": "Currency does not exist, please provide the valid code"
}
```

##### 500 Internal Server Error
```json
{
    "data": "Failed to find the currency, error details: ...."
}
```


## [05] 新增
### `POST: /api/currency`
#### Description: 輸入單一幣別資訊以新增資料至 DB，回傳 DB 中新增的幣別資訊
#### Parameters
| No  | Param             | Description               | Data Type           | Required      | Example         |
|---- |-----------------  |-------------------------  |-------------------- |----------  |---------------  |
| 1   | code      | 代碼| String               | Y                     |  JPY           |

| 2   | chineseName     | 中文名稱| String               | Y                     |  日幣           |

| 3   | symbol      | 符號| String               | Y                      |  ¥            |

| 4   | rate      | 匯率| BigDecimal               | Y                     |  40.0            |

| 5   | description     | 描述| String               | Y                      |  United States Dollar           |


#### Response Body
##### 200 OK
```json
{
    "data": {
        "code": "JPY",
        "chineseName": "日幣",
        "symbol": "¥",
        "rate": 40.000,
        "description": "Japanses Yen",
        "createTime": "2022-07-28T00:40:44.35",
        "updateTime": null
    }
}
```

##### 400 Bad Request
```json
{
    "data": "Currency already exist, please provide the valid input"
}
```

##### 500 Internal Server Error
```json
{
    "data": "Failed to insert the currency, error details: ...."
}
```

## [06] 更新
### `PUT: /api/currency/{code}`
#### Description: 輸入欲更新的單一幣別資訊以修改 DB，回傳 DB 中更新後的幣別資訊
#### Parameters
| No  | Param             | Description               | Data Type           | Required     | Example         |
|---- |-----------------  |-------------------------  |-------------------- |---------- |---------------  |
| 1   | code      | 代碼| String               | Y                     |  JPY           |

| 2   | chineseName     | 中文名稱| String               |                      |  測試           |

| 3   | symbol      | 符號| String               |                       |  ~            |

| 4   | rate      | 匯率| BigDecimal               |                       |  0.0            |

| 5   | description     | 描述| String               |                      |  Test          |


#### Response Body
##### 200 OK
```json
{
    "data": {
        "code": "JPY",
        "chineseName": "測試",
        "symbol": "~",
        "rate": 0.000,
        "description": "Test",
        "createTime": "2022-07-28T00:40:44.35",
        "updateTime": "2022-07-28T00:57:01.24"
    }
}
```

##### 400 Bad Request
```json
{
    "data": "Currency does not exist, please provide the valid code"
}
```

##### 500 Internal Server Error
```json
{
    "data": "Failed to update the currency, error details: ...."
}
```

## [07] 刪除
### `PUT: /api/currency/{code}`
#### Description: 輸入幣別代碼以刪除 DB 中的幣別資訊
#### Parameters
| No  | Param             | Description               | Data Type           | Required    | Example         |
|---- |-----------------  |-------------------------  |-------------------- |---------- |------------ |---------------  |
| 1   | code      | 代碼| String               | Y                    |  JPY           |


#### Response Body
##### 200 OK
```json
{
    "data": "Deleted successfully"
}
```

##### 400 Bad Request
```json
{
    "data": "Currency does not exist, please provide the valid code"
}
```

##### 500 Internal Server Error
```json
{
    "data": "Failed to delete the currency, error details: ...."
}
```
