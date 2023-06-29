package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;

    public PurchaseInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
    }

    @GetMapping("/list")
    public String list(Model model) {
// Purchase Invoices list:
        model.addAttribute("invoices", invoiceService.findAllPurchaseInvoices());

        return "invoice/purchase-invoice-list";
    }

//    @GetMapping("/create")
//    public String create(Model model) {
//
//        model.addAttribute("clientVendor", )
//    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {

// Invoice update Object:
        model.addAttribute("invoice", invoiceService.findById(id));



// InvoiceProduct list:
        model.addAttribute("invoiceProducts", invoiceProductService.findAll());

        return "invoice/purchase-invoice-update";
    }

}
