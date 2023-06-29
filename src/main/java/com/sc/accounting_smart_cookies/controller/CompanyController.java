package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.AddressDTO;
import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import com.sc.accounting_smart_cookies.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping("/list")
    public String listCompanies(Model model) {
        model.addAttribute("companies", companyService.listAllCompanies());
        return "/company/company-list";
    }

    @GetMapping("/create")
    public String createCompany(Model model) {
        model.addAttribute("newCompany", new CompanyDTO());
        model.addAttribute("address", new AddressDTO());

        return "/company/company-create";
    }

    @PostMapping("/create")
    public String insertCompany(@ModelAttribute("company") CompanyDTO companyDTO,  Model model) {

           companyService.create(companyDTO);
           model.addAttribute("companies", companyService.listAllCompanies());

           return "redirect:/companies/list";
    }

    @GetMapping("/update/{id}") ///companies/update/{id}
    public String updateCompany (@PathVariable ("id") Long id, Model model){

        model.addAttribute("company", companyService.findById(id));
        return "/company/company-update";
    }

    @PostMapping("update/{id}")
    public String updateCompany (@ModelAttribute ("company" ) CompanyDTO companyDTO, @PathVariable("id") Long id){
        companyService.update(companyDTO, id);
        return "redirect:/companies/list";
    }


 }


