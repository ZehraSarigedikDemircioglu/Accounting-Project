package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;
    private final InvoiceProductService invoiceProductService;
    private final CompanyService companyService;

    @GetMapping("/list")
    public String listSalesInvoices(Model model) {
// Sales Invoices list:
        model.addAttribute("invoices", invoiceService.findInvoicesByType(InvoiceType.SALES));

        return "invoice/sales-invoice-list";
    }

    @GetMapping("/create")
    public String createInvoice(Model model) {

        model.addAttribute("newSalesInvoice", invoiceService.getNewInvoice(InvoiceType.SALES));

        model.addAttribute("clients", clientVendorService.findVendorsByType(ClientVendorType.CLIENT));

        return "invoice/sales-invoice-create";
    }

    @PostMapping("/create")
    public String saveInvoice(@ModelAttribute("newSalesInvoice") InvoiceDTO invoiceDTO) {

        InvoiceDTO invoice = invoiceService.save(invoiceDTO, InvoiceType.SALES);

        return "redirect:/salesInvoices/update/" + invoice.getId();
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {

// Invoice update Object:
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("vendors", clientVendorService.findVendorsByType(ClientVendorType.CLIENT));

        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("products", productService.findAll());

// InvoiceProduct list:
        model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));

        return "invoice/sales-invoice-update";
    }

    @PostMapping("/update/{id}")
    public String updateList(@PathVariable("id") Long id, @ModelAttribute("invoice") InvoiceDTO invoiceDTO) {

        invoiceService.update(id, invoiceDTO);

        return "redirect:/salesInvoices/list";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO) {

        invoiceProductService.save(invoiceProductDTO, id);

        return "redirect:/salesInvoices/update/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long id) {

        invoiceService.deleteById(id);

        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
    public String deleteInvoiceProduct(@PathVariable("invoiceId") Long invoiceId,
                                       @PathVariable("invoiceProductId") Long invoiceProductId) {

        invoiceProductService.deleteById(invoiceProductId);

        return "redirect:/salesInvoices/update/" + invoiceId;
    }

    @GetMapping("/approve/{id}")
    public String approveInvoice(@PathVariable("id") Long id) {

        invoiceService.approveInvoiceById(id);

        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/print/{id}")
    public String printInvoice(@PathVariable("id") Long id, Model model) {

        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));

        model.addAttribute("company", companyService.getCompanyOfLoggedInUser());

        return "invoice/invoice_print";
    }

}