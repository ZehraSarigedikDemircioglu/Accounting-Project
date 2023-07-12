package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.service.ReportingService;
import com.sc.accounting_smart_cookies.service.StockReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class StockReportController {

    final private StockReportService stockReportService;
    private final ReportingService reportingService;

    public StockReportController(StockReportService stockReportService, ReportingService reportingService) {
        this.stockReportService = stockReportService;
        this.reportingService = reportingService;
    }

    @GetMapping("/stockData")
    public String listApprovedInvoiceList (Model model) {


        model.addAttribute("invoiceProducts",stockReportService.generateStockReport());

        return "report/stock-report";
    }

    @GetMapping("/profitLossData")
    public String getAllProfitLossReport(Model model) {
        model.addAttribute("monthlyProfitLossDataMap", reportingService.listMonthlyProfitLoss());
        return "report/profit-loss-report";
    }


}
