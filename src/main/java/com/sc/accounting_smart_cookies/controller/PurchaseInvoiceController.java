package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.service.ClientVendorService;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final ClientVendorService clientVendorService;

    public PurchaseInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ClientVendorService clientVendorService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String list(Model model) {
// Purchase Invoices list:
        model.addAttribute("invoices", invoiceService.findInvoicesByType(InvoiceType.PURCHASE));

        return "invoice/purchase-invoice-list";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("newPurchaseInvoice", invoiceService.getNewInvoice());

        model.addAttribute("vendors", clientVendorService.findVendorsByType(ClientVendorType.VENDOR));

        return "invoice/purchase-invoice-create";
    }

//    @PostMapping("/create")
//    public String insert(@ModelAttribute("newPurchaseInvoice") InvoiceDTO project, BindingResult bindingResult, Model model) {
//
//        if (bindingResult.hasErrors()) {
//
//            model.addAttribute("managers", userService.listAllByRole("manager"));
//            model.addAttribute("projects", projectService.listAllProjectDetails());
//
//            return "/project/create";
//        }
//        InvoiceDTO invoice = invoiceService.save(project);
//
//        return "redirect:/project/create/" + invoice.getId();
//
//    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {

// Invoice update Object:
        model.addAttribute("invoice", invoiceService.findById(id));



// InvoiceProduct list:
        model.addAttribute("invoiceProducts", invoiceProductService.findAll());

        return "invoice/purchase-invoice-update";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {

        invoiceService.deleteById(id);

        return "redirect:/purchaseInvoices/list";
    }

}
