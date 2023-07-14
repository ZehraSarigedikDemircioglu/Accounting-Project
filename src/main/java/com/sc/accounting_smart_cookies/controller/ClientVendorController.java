package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.ClientVendorDTO;
import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import com.sc.accounting_smart_cookies.service.AddressService;
import com.sc.accounting_smart_cookies.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;

    private final AddressService addressService;

    public ClientVendorController(ClientVendorService clientVendorService, AddressService addressService) {
        this.clientVendorService = clientVendorService;
        this.addressService = addressService;
    }

    @GetMapping("/list")
    public String listClientVendor(Model model){
        model.addAttribute("clientVendors", clientVendorService.findAll());
        return "clientVendor/clientVendor-list";
    }
    @GetMapping("/create")
    public String createClientVendor(Model model){
        model.addAttribute("newClientVendor", new ClientVendorDTO());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        model.addAttribute("countries", addressService.retrieveCountyList());
        return "clientVendor/clientVendor-create";
    }
    @PostMapping("/create")
    public String saveClientVendor(@Valid @ModelAttribute("newClientVendor") ClientVendorDTO clientVendorDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
            model.addAttribute("countries", addressService.retrieveCountyList());
            return "clientVendor/clientVendor-create";
        }
        clientVendorService.save(clientVendorDTO);
//        return "redirect:/clientVendor/clientVendor-list";
        return "redirect:/clientVendors/list";
    }
    @GetMapping("/update/{id}")
    public String editClientVendor(@PathVariable("id") Long id, Model model){
        model.addAttribute("clientVendor", clientVendorService.findById(id));
        model.addAttribute("clientVendors", clientVendorService.findAll());
        model.addAttribute("countries", addressService.retrieveCountyList());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        return "clientVendor/clientVendor-update";
    }
    @PostMapping("/update/{id}")
    public String updateClientVendor(@Valid @ModelAttribute("clientVendor") ClientVendorDTO clientVendorDTO, BindingResult bindingResult, @PathVariable("id") Long id, Model model){
        if (clientVendorService.isClientVendorByCompanyNameExist(clientVendorDTO)) {
            bindingResult.rejectValue("clientVendorName", "",
                    "This Client/Vendor in current company already exists.");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("countries", addressService.retrieveCountyList());
            return "clientVendor/clientVendor-update";
        }
        clientVendorService.update(id, clientVendorDTO);
        return "redirect:/clientVendors/list";
    }



    @GetMapping("/delete/{id}")
    public String deleteClientVendor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        if(clientVendorService.isClientVendorHasInvoice(id)){
            redirectAttributes.addFlashAttribute("error", "You cannot delete this Client/Vendor");
        return "redirect:/clientVendors/list";
    }
        clientVendorService.deleteById(id);
        return "redirect:/clientVendors/list";
    }

    @ModelAttribute()
    public void commonModelAttribute(Model model){
        model.addAttribute("countries", addressService.retrieveCountyList());
    }


}
