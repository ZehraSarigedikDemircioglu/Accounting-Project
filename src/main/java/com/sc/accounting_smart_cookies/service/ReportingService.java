package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.entity.InvoiceProduct;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;

public interface ReportingService {
    BigDecimal setProfitLossOfInvoiceProductsForSalesInvoice(InvoiceProduct toBeSoldProduct);
    Map<String, BigDecimal> listMonthlyProfitLoss();



}
