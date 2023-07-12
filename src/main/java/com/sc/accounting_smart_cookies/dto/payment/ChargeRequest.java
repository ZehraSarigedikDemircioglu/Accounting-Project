package com.sc.accounting_smart_cookies.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeRequest {
    public enum Currency {
        EUR, USD;
    }
    private String description;
    private int amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;

}