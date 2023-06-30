package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.CategoryDTO;
import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.enums.ProductUnit;
import com.sc.accounting_smart_cookies.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/list")
    public String listCategories(Model model){

        model.addAttribute("categories", categoryService.listAllCategories());

        return "category/category-list";
    }

    @GetMapping("/create")
    public String createCategories(Model model){

        model.addAttribute("newCategory", new CategoryDTO());

        return "category/category-create";
    }

    @PostMapping("/create")
    public String saveCategory(@ModelAttribute("category") CategoryDTO categoryDTO){
        categoryService.save(categoryDTO);
        return "redirect:/categories/list";
    }

    @GetMapping("/update/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model){

        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("products", categoryService.listAllCategories());

        return "category/category-update";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, @ModelAttribute("category") CategoryDTO categoryDTO){
        categoryService.update(categoryDTO, id);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteById(id);
        return "redirect:/categories/list";
    }


}
