package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.service.ReportingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping("/profitLossData")
    public String getAllProfitLossReport(Model model) {
        model.addAttribute("monthlyProfitLossDataMap", reportingService.listMonthlyProfitLoss());
        return "report/profit-loss-report";
    }
}
