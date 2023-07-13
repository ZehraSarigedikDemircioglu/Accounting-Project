package com.sc.accounting_smart_cookies.service;


import java.math.BigDecimal;

import java.util.Map;

public interface ReportingService {

    Map<String, BigDecimal> listMonthlyProfitLoss();

    BigDecimal getAllProfitLoss();

    BigDecimal getTotalCost();

    BigDecimal getTotalSales();



}