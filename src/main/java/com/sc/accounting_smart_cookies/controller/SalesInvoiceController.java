package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.service.ClientVendorService;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import com.sc.accounting_smart_cookies.service.ProductService;
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

    @GetMapping("/list")
    public String listSalesInvoices(Model model) {
// Sales Invoices list:
        model.addAttribute("invoices", invoiceService.findInvoicesByType(InvoiceType.SALES));

        return "invoice/sales-invoice-list";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("newSalesInvoice", invoiceService.getNewInvoice(InvoiceType.SALES));

        model.addAttribute("clients", clientVendorService.findVendorsByType(ClientVendorType.CLIENT));

        return "invoice/sales-invoice-create";
    }

    @PostMapping("/create")
    public String insert(@ModelAttribute("newSalesInvoice") InvoiceDTO invoiceDTO) {

        InvoiceDTO invoice = invoiceService.save(invoiceDTO, InvoiceType.SALES);

        return "redirect:/salesInvoices/update/" + invoice.getId();
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {

// Invoice update Object:
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("clients", clientVendorService.findVendorsByType(ClientVendorType.CLIENT));

        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("products", productService.findAll());

// InvoiceProduct list:
        model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));

        return "invoice/sales-invoice-update";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("invoice") InvoiceDTO invoiceDTO, Model model) {

// Invoice update Object:
        model.addAttribute("invoice", invoiceDTO);
        model.addAttribute("vendors", clientVendorService.findVendorsByType(ClientVendorType.CLIENT));

        model.addAttribute("newInvoiceProduct", invoiceProductService.findById(id));
        model.addAttribute("products", productService.findAllByInvoice(invoiceDTO));

// InvoiceProduct list:
        model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));

        return "redirect:/salesInvoices/addInvoiceProduct/" + invoiceDTO.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long id) {

        invoiceService.deleteById(id);

        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
    public String deleteInvoiceProduct(@PathVariable("invoiceId") Long invoiceId,
                                       @PathVariable("invoiceProductId") Long invoiceProductId) {

        invoiceService.deleteById(invoiceId);

        return "redirect:/purchaseInvoices/list";
    }
}
