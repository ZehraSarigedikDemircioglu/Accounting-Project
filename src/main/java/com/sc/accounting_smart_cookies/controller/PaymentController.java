package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.PaymentDto;
import com.sc.accounting_smart_cookies.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping({"/list", "/list/{year}"})
    public String list(@RequestParam(value = "year", required = false) String selectedYear, Model model) {

        int selectedYear1 = (selectedYear == null || selectedYear.isEmpty()) ? LocalDate.now().getYear() : Integer.parseInt(selectedYear);
        paymentService.createPaymentsIfNotExist(selectedYear1);
        model.addAttribute("payments",paymentService.getAllPaymentsByYear(selectedYear1));
        model.addAttribute("year", selectedYear1);
        return "payment/payment-list";
    }


    @GetMapping("/newpayment/{id}")
    public String checkout(@PathVariable("id") Long id, Model model) {

        PaymentDto dto = paymentService.getPaymentById(id);
        model.addAttribute("payment", dto);

        return "payment/payment-method";
    }






}