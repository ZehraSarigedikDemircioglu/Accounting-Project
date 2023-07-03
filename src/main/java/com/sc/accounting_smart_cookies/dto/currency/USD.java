package com.sc.accounting_smart_cookies.dto.currency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class USD {

    private BigDecimal eur;
    private BigDecimal gbp;
    private BigDecimal inr;
    private BigDecimal jpy;
    private BigDecimal cad;
}
