package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.annotation.LoggingAnnotation;
import com.sc.accounting_smart_cookies.client.CountryClient;
import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.service.AddressService;
import com.sc.accounting_smart_cookies.service.CompanyService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    private final AddressService addressService;


    public CompanyController(CompanyService companyService, CountryClient countryClient, AddressService addressService) {
        this.companyService = companyService;

        this.addressService = addressService;
    }


    @GetMapping("/list")
    public String listCompanies(Model model) {
        model.addAttribute("companies", companyService.listAllCompanies());
        return "/company/company-list";
    }

    @GetMapping("/create")
    public String createCompany(Model model) {
        model.addAttribute("newCompany", new CompanyDTO());
//        model.addAttribute("address", new AddressDTO());
        model.addAttribute("countries", addressService.retrieveCountyList());

        return "/company/company-create";
    }

    @PostMapping("/create")
    public String insertCompany(@Valid @ModelAttribute("newCompany") CompanyDTO companyDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "company/company-create";
        }

        try {
            companyService.create(companyDTO);
        } catch (DataIntegrityViolationException e) {
            bindingResult.rejectValue("title", "duplicate.title", "Title already exists");
            return "company/company-create";
        }
        return "redirect:/companies/list";
    }


    @GetMapping("/update/{id}") ///companies/update/{id}
    public String updateCompany(@PathVariable("id") Long id, Model model) {

        model.addAttribute("company", companyService.findById(id));
        return "/company/company-update";
    }

    @PostMapping("update/{id}")
    public String updateCompany(@Valid @ModelAttribute("company") CompanyDTO companyDTO, BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "company/company-update";
        }
        try {
            companyService.update(companyDTO, id);
        } catch (DataIntegrityViolationException e) {
            bindingResult.rejectValue("title", "duplicate.title", "Title already exists");
            return "company/company-update";
        }

        return "redirect:/companies/list";
    }

    @LoggingAnnotation
    @RequestMapping(value = "/activate/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String activateCompany(@PathVariable("id") Long companyId) {
        companyService.activateCompany(companyId);
        return "redirect:/companies/list";
    }

    @LoggingAnnotation
    @RequestMapping(value = "/deactivate/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String deactivateCompany(@PathVariable("id") Long companyId) {
        companyService.deactivateCompany(companyId);
        return "redirect:/companies/list";
    }

    @ModelAttribute()
    public void commonModelAttribute(Model model){
        model.addAttribute("countries", addressService.retrieveCountyList());
    }

}


