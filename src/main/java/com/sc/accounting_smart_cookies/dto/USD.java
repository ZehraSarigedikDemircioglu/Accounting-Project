package com.sc.accounting_smart_cookies.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class USD {

    private BigDecimal euro;
    private BigDecimal britishPound;
    private BigDecimal canadianDollar;
    private BigDecimal japaneseYen;
    private BigDecimal indianRupee;
}
