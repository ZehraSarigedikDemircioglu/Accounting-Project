package com.sc.accounting_smart_cookies.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum InvoiceStatus {

    AWAITING_APPROVAL("Awaiting Approval"),

    APPROVED("Approved");

    private String value;

    InvoiceStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}