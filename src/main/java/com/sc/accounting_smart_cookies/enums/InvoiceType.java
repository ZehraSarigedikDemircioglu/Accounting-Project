package com.sc.accounting_smart_cookies.enums;

public enum InvoiceType {

    PURCHASE("Purchase"),
    SALES("Sales");

    private String value;

    InvoiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}