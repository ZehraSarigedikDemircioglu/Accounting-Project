package com.sc.accounting_smart_cookies.enums;

import lombok.Getter;

@Getter
public enum ClientVendorType {

    VENDOR("Vendor"), CLIENT("Client");
    private final String value;

    public String getValue() {
        return value;
    }

    ClientVendorType(String value) {
        this.value = value;
    }
}
