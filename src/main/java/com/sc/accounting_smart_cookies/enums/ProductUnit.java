package com.sc.accounting_smart_cookies.enums;

public enum ProductUnit {
    LBS("Libre"), GALLON("Gallon"), PCS("Pieces"), KG("Kilogram"), METER("Meter"), INCH("Inch"), FEET("Feet");

    private final String value;

    ProductUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
