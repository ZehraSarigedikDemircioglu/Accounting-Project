package com.sc.accounting_smart_cookies.exceptions;

public class ClientVendorNotFoundException extends RuntimeException{
    public ClientVendorNotFoundException(String message) {
        super(message);
    }
}
