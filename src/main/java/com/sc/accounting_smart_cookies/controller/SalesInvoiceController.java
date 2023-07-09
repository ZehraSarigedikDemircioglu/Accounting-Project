package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

        model.addAttribute("clients", clientVendorService.findAll());

        return "invoice/sales-invoice-create";
    }

    @PostMapping("/create")
    public String saveInvoice(@Valid @ModelAttribute("newSalesInvoice") InvoiceDTO invoiceDTO,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", clientVendorService.findAll());
            return "invoice/sales-invoice-create";
        }

        InvoiceDTO invoice = invoiceService.save(invoiceDTO, InvoiceType.SALES);

        return "redirect:/salesInvoices/update/" + invoice.getId();
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {

// Invoice update Object:
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("clients", clientVendorService.findAll());

        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("products", productService.findAllByCompany());

// InvoiceProduct list:
        model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));

        return "invoice/sales-invoice-update";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String update(@Valid @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO,
                         BindingResult bindingResult, @PathVariable("id") Long id, Model model) {

        if (invoiceProductService.insufficientQuantity(invoiceProductDTO)) {
            bindingResult.rejectValue("quantity", " ",
                    "Insufficient quantity of: " + invoiceProductDTO.getProduct().getName());
        }

        if (bindingResult.hasErrors()) {

            model.addAttribute("invoice", invoiceService.findById(id));
            model.addAttribute("clients", clientVendorService.findAll());

            model.addAttribute("newInvoiceProduct", invoiceProductDTO);
            model.addAttribute("products", productService.findAllByCompany());

            model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));

            return "invoice/sales-invoice-update";
        }

        invoiceProductService.save(invoiceProductDTO, id);

        return "redirect:/salesInvoices/update/" + id;
    }

    @PostMapping("/update/{id}")
    public String updateList(@PathVariable("id") Long id, @ModelAttribute("invoice") InvoiceDTO invoiceDTO) {

        invoiceService.update(id, invoiceDTO);

        return "redirect:/salesInvoices/list";
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

//        productService.updateProductQuantity();

        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/print/{id}")
    public String printInvoice(@PathVariable("id") Long id, Model model) {

        model.addAttribute("invoice", invoiceService.printInvoice(id));
        model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoiceId(id));

        model.addAttribute("company", companyService.getCompanyOfLoggedInUser());

        return "invoice/invoice_print";
    }

}
