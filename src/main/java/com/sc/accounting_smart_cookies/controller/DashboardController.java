package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.client.ExchangeRateClient;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        //model.addAttribute("Total Cost", invoiceService.totalCost());
        //model.addAttribute("Total Sales", invoiceService.totalSales());
        //model.addAttribute("Total Profit/Loss", invoiceService.totalSales()-invoiceService.totalCost());
        //model.addAttribute("Last Transactions", invoiceService.listInvoices());
        model.addAttribute("Exchange Rates", exchangeRateClient.getExchangeRates());


        return "/dashboard";
    }

}
