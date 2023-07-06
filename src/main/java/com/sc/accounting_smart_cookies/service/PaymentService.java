package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.PaymentDto;

import java.util.List;

public interface PaymentService {

    List<PaymentDto> getAllPaymentsByYear(int year);

    void createPaymentsIfNotExist(int year);

    PaymentDto getPaymentById(Long id);

    PaymentDto updatePayment(Long id);
}
