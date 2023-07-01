package com.sc.accounting_smart_cookies.converter;

import com.sc.accounting_smart_cookies.dto.CategoryDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceProductDTOConverter implements Converter<String, InvoiceProductDTO> {

    private final InvoiceProductService invoiceProductService;

    public InvoiceProductDTOConverter(@Lazy InvoiceProductService invoiceProductService) {
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public InvoiceProductDTO convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }
        return invoiceProductService.findById(Long.parseLong(source));
    }
}
