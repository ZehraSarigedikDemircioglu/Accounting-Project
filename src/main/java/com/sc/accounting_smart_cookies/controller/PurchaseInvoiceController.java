package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.service.*;
import lombok.AllArgsConstructor;
import org.bouncycastle.math.raw.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;
    private final CompanyService companyService;

    @GetMapping("/list")
    public String listPurchaseInvoices(Model model) {
// Purchase Invoices list:
        model.addAttribute("invoices", invoiceService.findInvoicesByType(InvoiceType.PURCHASE));

        return "invoice/purchase-invoice-list";
    }

    @GetMapping("/create")
    public String createInvoice(Model model) {

        model.addAttribute("newPurchaseInvoice", invoiceService.getNewInvoice(InvoiceType.PURCHASE));

        model.addAttribute("vendors", clientVendorService.findAll());

        return "invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String saveInvoice(@Valid @ModelAttribute("newPurchaseInvoice") InvoiceDTO invoiceDTO,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("vendors", clientVendorService.findAll());
            return "invoice/purchase-invoice-create";
        }

        InvoiceDTO invoice = invoiceService.save(invoiceDTO, InvoiceType.PURCHASE);

        return "redirect:/purchaseInvoices/update/" + invoice.getId();

    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {

// Invoice update Object:
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("vendors", clientVendorService.findAll());

        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("products", productService.findAllByCompany());

// InvoiceProduct list:
        model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));

        return "invoice/purchase-invoice-update";
    }

    @PostMapping("/update/{id}")
    public String updateList(@PathVariable("id") Long id, @ModelAttribute("invoice") InvoiceDTO invoiceDTO) {

        invoiceService.update(id, invoiceDTO);

        return "redirect:/purchaseInvoices/list";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String update(@Valid @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO,
                         BindingResult bindingResult, @PathVariable("id") Long id, Model model) {
//        if(invoiceProductService.isValidQuantity(invoiceProductDTO)){
//            bindingResult.rejectValue("quantity","","Quantity cannot be greater than 100 or less than 1. ");
//        }


        if (bindingResult.hasErrors()) {

            model.addAttribute("invoice", invoiceService.findById(id));
            model.addAttribute("vendors", clientVendorService.findAll());

            model.addAttribute("newInvoiceProduct",  invoiceProductDTO);
            model.addAttribute("products", productService.findAllByCompany());

// InvoiceProduct list:
            model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));


            return "invoice/purchase-invoice-update";
        }

        invoiceProductService.save(invoiceProductDTO, id);

        return "redirect:/purchaseInvoices/update/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long id) {

        invoiceService.deleteById(id);

        return "redirect:/purchaseInvoices/list";
    }

    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
    public String deleteInvoiceProduct(@PathVariable("invoiceId") Long invoiceId,
                                       @PathVariable("invoiceProductId") Long invoiceProductId) {

        invoiceProductService.deleteById(invoiceProductId);

        return "redirect:/purchaseInvoices/update/" + invoiceId;
    }

    @GetMapping("/approve/{id}")
    public String approveInvoice(@PathVariable("id") Long id) {

        invoiceService.approveInvoiceById(id);

//        productService.updateProductQuantity();

        return "redirect:/purchaseInvoices/list";
    }

    @GetMapping("/print/{id}")
    public String printInvoice(@PathVariable("id") Long id, Model model) {

        model.addAttribute("invoice", invoiceService.printInvoice(id));
        model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));

        model.addAttribute("company", companyService.getCompanyOfLoggedInUser());

        return "invoice/invoice_print";
    }

}
