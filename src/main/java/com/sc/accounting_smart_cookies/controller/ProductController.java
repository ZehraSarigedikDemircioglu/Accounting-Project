package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.enums.ProductUnit;
import com.sc.accounting_smart_cookies.service.CategoryService;
import com.sc.accounting_smart_cookies.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/create")
    public String createProduct(Model model) {
        model.addAttribute("newProduct", new ProductDTO());
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
        model.addAttribute("categories", categoryService.listAllCategories());
        return "product/product-create";
    }

    @PostMapping("/create")
    public String saveProduct(@Valid @ModelAttribute("newProduct") ProductDTO productDTO,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
            model.addAttribute("categories", categoryService.listAllCategories());
            return "product/product-create";
        }
        productService.save(productDTO);
        return "redirect:/products/list";
    }

    @GetMapping("/update/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
        model.addAttribute("categories", categoryService.listAllCategories());

        return "product/product-update";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("product") ProductDTO productDTO,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product/product-update";
        }
        productService.update(id, productDTO);
        return "redirect:/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/products/list";
    }

}
