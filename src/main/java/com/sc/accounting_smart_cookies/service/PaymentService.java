package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.PaymentDto;
import com.sc.accounting_smart_cookies.dto.payment.ChargeRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import javax.naming.AuthenticationException;
import java.util.List;

public interface PaymentService {

    List<PaymentDto> getAllPaymentsByYear(int year);

    void createPaymentsIfNotExist(int year);

    PaymentDto getPaymentById(Long id);

    PaymentDto updatePayment(Long id);

    Charge charge(ChargeRequest chargeRequest) throws StripeException, AuthenticationException;
}
