package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.dto.PaymentDto;
import com.sc.accounting_smart_cookies.dto.payment.ChargeRequest;
import com.sc.accounting_smart_cookies.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.StripeError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.AuthenticationException;
import java.time.LocalDate;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    @Value("${Stripe_Public_Key}")
    private String api_Key;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping({"/list", "/list/{year}"})
    public String list(@RequestParam(value = "year", required = false) String selectedYear, Model model) {

        int selectedYear1 = (selectedYear == null || selectedYear.isEmpty()) ? LocalDate.now().getYear() : Integer.parseInt(selectedYear);
        paymentService.createPaymentsIfNotExist(selectedYear1);
        model.addAttribute("payments", paymentService.getAllPaymentsByYear(selectedYear1));
        model.addAttribute("year", selectedYear1);
        return "payment/payment-list";
    }


    @GetMapping("/newpayment/{modelId}")
    public String checkout(@PathVariable("modelId") Long id, Model model) {

        PaymentDto dto = paymentService.getPaymentById(id);
        int amount = dto.getAmount();
        model.addAttribute("amount", amount);
        model.addAttribute("stripePublicKey", api_Key);
        model.addAttribute("currency", ChargeRequest.Currency.USD);

        return "payment/payment-method";
    }

    @PostMapping("/charge/{modelId}")
    public String charge(@PathVariable("modelId") PaymentDto dto,
                         RedirectAttributes redirectAttributes, ChargeRequest chargeRequest, Model model)
            throws StripeException, AuthenticationException {

        chargeRequest.setCurrency(ChargeRequest.Currency.valueOf(String.valueOf(ChargeRequest.Currency.EUR)));
        chargeRequest.setDescription("Cydeo accounting subscription fee for : " + dto.getYear().getYear() + " " + dto.getMonth());
        Charge charge = paymentService.charge(chargeRequest);
        model.addAttribute("id", dto.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        model.addAttribute("description", charge.getDescription());
        if (!charge.getStatus().equals("succeeded")) {
            redirectAttributes.addFlashAttribute("error", new StripeError().getMessage());
            return "payment/payment-result";
        }
        int selectedYear1 = LocalDate.now().getYear();
        paymentService.createPaymentsIfNotExist(selectedYear1);
        model.addAttribute("payments", paymentService.getAllPaymentsByYear(selectedYear1));
        model.addAttribute("year", selectedYear1);

        paymentService.updatePayment(dto.getId());
        return "redirect:/payments/list";
    }

    @GetMapping("/toInvoice/{modelId}")
    public String getInvoice(@PathVariable("modelId") PaymentDto dto,Model model) {
        model.addAttribute("payment", paymentService.getPaymentById(dto.getId()));
        model.addAttribute("company", paymentService.getPaymentById(dto.getId()).getCompany());
        return "payment/payment-invoice";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(StripeException ex) {

        return "payment/payment-result";
    }

}