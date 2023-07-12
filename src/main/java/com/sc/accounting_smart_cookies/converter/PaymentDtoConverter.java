package com.sc.accounting_smart_cookies.converter;

import com.sc.accounting_smart_cookies.dto.PaymentDto;
import com.sc.accounting_smart_cookies.service.PaymentService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentDtoConverter implements Converter<String, PaymentDto> {

    private final PaymentService paymentService;

    public PaymentDtoConverter(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @Override
    public PaymentDto convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }
        return paymentService.getPaymentById(Long.parseLong(source));
    }
}