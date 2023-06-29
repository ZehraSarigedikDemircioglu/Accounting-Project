package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.service.RoleService;
import com.sc.accounting_smart_cookies.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final RoleService roleService;
    private final UserService userService;
//    private final CompanyService companyService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
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

        model.addAttribute("userRoles", roleService.getAllRoles());

//        model.addAttribute("companies",companyService.listAllCompanies());

        model.addAttribute("users", userService.getAllUsers());

        return "user/user-update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") UserDTO dto, @PathVariable("id") Long id) {

        userService.updateUser(dto);
        return "redirect:/users/list";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("newUser", new UserDTO());

        model.addAttribute("userRoles", roleService.getAllRoles());

//        model.addAttribute("companies",companyService.listAllCompanies());

        return "/user/user-create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") UserDTO dto) {
        userService.save(dto);
        return "redirect:/users/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users/list";
    }
}

