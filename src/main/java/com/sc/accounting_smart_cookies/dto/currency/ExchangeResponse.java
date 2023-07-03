package com.sc.accounting_smart_cookies.dto.currency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeResponse {

    private String date;
    private USD usd;
}
