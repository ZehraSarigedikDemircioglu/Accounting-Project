package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.service.CompanyService;
import com.sc.accounting_smart_cookies.service.RoleService;
import com.sc.accounting_smart_cookies.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final RoleService roleService;
    private final UserService userService;
    private final CompanyService companyService;

    public UserController(RoleService roleService, UserService userService, CompanyService companyService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String getAllUsers(Model model) {

        model.addAttribute("roles", roleService.getAllRoles());

        model.addAttribute("users", userService.getAllUsers());

        return "user/user-list";
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {

        model.addAttribute("user", userService.findById(id));

        model.addAttribute("userRoles", roleService.getAllRolesForCurrentUser());

        model.addAttribute("companies", companyService.getAllCompaniesForCurrentUser());

        model.addAttribute("users", userService.getAllUsers());

        return "user/user-update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") UserDTO dto, BindingResult bindingResult, @PathVariable("id") Long id, Model model) {
        boolean emailExist=userService.findByUsername1(dto.getUsername());

        if (bindingResult.hasErrors()) {
            if (emailExist) {
                bindingResult.rejectValue("username", " ", "A user with this email already exists. Please try with different email.");
            }

            model.addAttribute("userRoles", roleService.getAllRolesForCurrentUser());

            model.addAttribute("companies", companyService.getAllCompaniesForCurrentUser());

            model.addAttribute("users", userService.getAllUsers());
            return "user/user-update";

        }
        dto.setUsername(dto.getUsername());
        userService.updateUser(dto);
        return "redirect:/users/list";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("user", new UserDTO());

        model.addAttribute("userRoles", roleService.getAllRolesForCurrentUser());

        model.addAttribute("companies", companyService.getAllCompaniesForCurrentUser());

        return "user/user-create";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user") UserDTO dto, BindingResult bindingResult, Model model) {
        boolean emailExist=userService.findByUsername1(dto.getUsername());
        if (bindingResult.hasErrors()) {
            if (emailExist) {
                bindingResult.rejectValue("username", " ", "A user with this email already exists. Please try with different email.");
            }

            model.addAttribute("userRoles", roleService.getAllRolesForCurrentUser());

            model.addAttribute("companies", companyService.getAllCompaniesForCurrentUser());

            return "/user/user-create";
        }

        userService.save(dto);

        return "redirect:/users/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users/list";
    }
}

