package com.sc.accounting_smart_cookies.exceptions;

public class ProductLowLimitAlertException extends RuntimeException{
    public ProductLowLimitAlertException(String message) {
        super(message);
    }
}
