package com.sc.accounting_smart_cookies.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRates {

    private BigDecimal euro;
    private BigDecimal britishPound;
    private BigDecimal canadianDollar;
    private BigDecimal japaneseYen;
    private BigDecimal indianRupee;

}
