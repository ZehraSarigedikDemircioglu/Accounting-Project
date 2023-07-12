package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.service.StockReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class StockReportController {

    final private StockReportService stockReportService;

    public StockReportController(StockReportService stockReportService) {
        this.stockReportService = stockReportService;
    }


    @GetMapping("/stockData")
    public String listApprovedInvoiceList (Model model) {


        model.addAttribute("invoiceProducts",stockReportService.generateStockReport());

        return "report/stock-report";
    }


}
