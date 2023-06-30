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
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;

    @GetMapping("/list")
    public String list(Model model) {

// Purchase Invoices list:
        model.addAttribute("invoices", invoiceService.findInvoicesByType(InvoiceType.PURCHASE));

        return "invoice/purchase-invoice-list";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("newPurchaseInvoice", invoiceService.getNewInvoice(InvoiceType.PURCHASE));

        model.addAttribute("vendors", clientVendorService.findVendorsByType(ClientVendorType.VENDOR));

        return "invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String insert(@ModelAttribute("newPurchaseInvoice") InvoiceDTO invoiceDTO) {

        InvoiceDTO invoice = invoiceService.save(invoiceDTO, InvoiceType.PURCHASE);

        return "redirect:/purchaseInvoices/update/" + invoice.getId();

    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {

// Invoice update Object:
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("vendors", clientVendorService.findVendorsByType(ClientVendorType.VENDOR));

        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("products", productService.findAll());

// InvoiceProduct list:
        model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));

        return "invoice/purchase-invoice-update";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("invoice") InvoiceDTO invoiceDTO,
                         @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO, Model model) {

// Invoice update Object:
        model.addAttribute("invoice", invoiceDTO);
        model.addAttribute("vendors", clientVendorService.findVendorsByType(ClientVendorType.VENDOR));

        model.addAttribute("newInvoiceProduct", invoiceProductService.findById(id));
        model.addAttribute("products", productService.findAll());

// InvoiceProduct list:
        model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));

        invoiceProductService.save(invoiceProductDTO);

        return "redirect:/purchaseInvoices/addInvoiceProduct/" + invoiceDTO.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {

        invoiceService.deleteById(id);

        return "redirect:/purchaseInvoices/list";
    }

//    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
//    public String delete(@PathVariable("invoiceId") Long invoiceId, @PathVariable("invoiceProductId") Long invoiceProductId) {
//
//        invoiceService.deleteById(invoiceId);
//ty
//        return "redirect:/purchaseInvoices/list";
//    }

}
