package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    private final InvoiceService invoiceService;

    public SalesInvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/list")
    public String listSalesInvoices(Model model) {
// Sales Invoices list:
        model.addAttribute("invoices", invoiceService.findInvoicesByType(InvoiceType.SALES));

        return "invoice/sales-invoice-list";
    }
}
