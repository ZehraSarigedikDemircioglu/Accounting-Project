package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.client.ExchangeRateClient;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import com.sc.accounting_smart_cookies.service.ReportingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final InvoiceService invoiceService;
    private final ExchangeRateClient exchangeRateClient;

    private final ReportingService reportingService;

    public DashboardController(InvoiceService invoiceService, ExchangeRateClient exchangeRateClient, ReportingService reportingService) {
        this.invoiceService = invoiceService;
        this.exchangeRateClient = exchangeRateClient;
        this.reportingService = reportingService;
    }

    @GetMapping
    public String method1(Model model) {

        Map<String, BigDecimal> summaryNumbers = new HashMap<>();

        summaryNumbers.put("profitLoss", reportingService.getAllProfitLoss());
        summaryNumbers.put("totalCost", reportingService.getTotalCost());
        summaryNumbers.put("totalSales", reportingService.getTotalSales());

        model.addAttribute("summaryNumbers", summaryNumbers);
        model.addAttribute("invoices", invoiceService.findTop3ByCompanyOrderByDateDesc());
        model.addAttribute("exchangeRates", exchangeRateClient.getExchangeRates());

        return "/dashboard";
    }

}
