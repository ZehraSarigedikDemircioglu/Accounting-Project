package com.sc.accounting_smart_cookies.exceptions;

public class InvoiceProductNotFoundException extends RuntimeException{
    public InvoiceProductNotFoundException(String message) {
        super(message);
    }
}
