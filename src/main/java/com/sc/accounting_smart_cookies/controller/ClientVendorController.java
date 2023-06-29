package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.ClientVendorDTO;
import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import com.sc.accounting_smart_cookies.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;

    public ClientVendorController(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
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
        return "clientVendor/clientVendor-create";
    }
    @PostMapping("/create")
    public String saveClientVendor(@ModelAttribute("newClientVendor") ClientVendorDTO clientVendorDTO){
        clientVendorService.save(clientVendorDTO);
//        return "redirect:/clientVendor/clientVendor-list";
        return "redirect:/clientVendors/list";
    }
    @GetMapping("/update/{id}")
    public String editClientVendor(@PathVariable("id") Long id, Model model){
        model.addAttribute("clientVendor", clientVendorService.findById(id));
        model.addAttribute("clientVendors", clientVendorService.findAll());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        return "clientVendor/clientVendor-update";
    }
    @PostMapping("/update/{id}")
    public String updateClientVendor(@ModelAttribute("clientVendor") ClientVendorDTO clientVendorDTO, @PathVariable("id") Long id){
        clientVendorService.update(id, clientVendorDTO);
//        return "redirect:/clientVendor/clientVendor-list";
        return "redirect:/clientVendors/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteClientVendor(@PathVariable("id") Long id){
        clientVendorService.deleteById(id);
        return "redirect:/clientVendor/clientVendor-list";
    }
}
