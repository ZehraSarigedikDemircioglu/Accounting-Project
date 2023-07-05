package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.client.ExchangeRateClient;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final InvoiceService invoiceService;
    private final ExchangeRateClient exchangeRateClient;

    public DashboardController(InvoiceService invoiceService, ExchangeRateClient exchangeRateClient) {
        this.invoiceService = invoiceService;
        this.exchangeRateClient = exchangeRateClient;
    }

    @GetMapping
    public String method1(Model model) {

        Map<String,Integer> summaryNumbers = new HashMap<>();
        summaryNumbers.put("totalCost", 2000);
        summaryNumbers.put("totalSales", 2000);
        summaryNumbers.put("profitLoss", 2000);
        model.addAttribute("summaryNumbers", summaryNumbers);
        model.addAttribute("invoices", invoiceService.findTop3ByOrderByDateDesc());
        model.addAttribute("exchangeRates", exchangeRateClient.getExchangeRates());


        return "/dashboard";
    }

}
