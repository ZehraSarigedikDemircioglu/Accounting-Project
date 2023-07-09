package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.annotation.ExecutionTime;
import com.sc.accounting_smart_cookies.annotation.LoggingAnnotation;
import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.enums.ProductUnit;
import com.sc.accounting_smart_cookies.service.CategoryService;
import com.sc.accounting_smart_cookies.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    //    @GetMapping("/list")
//    public String listProduct(Model model) {
//        model.addAttribute("products", productService.findAll());
//        return "product/product-list";
//    }
    @GetMapping("/list")
    public String listProductByCompany(Model model) {
        model.addAttribute("products", productService.findAllByCompany());
        return "product/product-list";
    }

    @ExecutionTime
    @GetMapping("/create")
    public String createProduct(Model model) {
        model.addAttribute("newProduct", new ProductDTO());
        return "product/product-create";
    }

    @ExecutionTime
    @PostMapping("/create")
    public String saveProduct(@Valid @ModelAttribute("newProduct") ProductDTO productDTO,
                              BindingResult bindingResult, Model model) {

        if (productService.isProductNameExist(productDTO)) {
            bindingResult.rejectValue("name", " ", "This Product Name already exists.");
        }

        if (bindingResult.hasErrors()) {
            return "product/product-create";
        }
        productService.save(productDTO);
        return "redirect:/products/list";
    }

    @GetMapping("/update/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product/product-update";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("product") ProductDTO productDTO,
                                BindingResult bindingResult) {
        if (productService.isProductNameExist(productDTO)) {
            bindingResult.rejectValue("name", " ", "This Product Name already exists.");
        }
        if (bindingResult.hasErrors()) {
            return "product/product-update";
        }
        productService.update(id, productDTO);
        return "redirect:/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (productService.checkProductHasInvoice(id)) {
            redirectAttributes.addFlashAttribute("error", "This product can not be deleted.");
            return "redirect:/products/list";
        }
        productService.deleteById(id);
        return "redirect:/products/list";
    }

    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
        model.addAttribute("categories", categoryService.listAllCategories());
    }

}
