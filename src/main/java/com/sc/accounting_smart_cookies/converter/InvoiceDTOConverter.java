package com.sc.accounting_smart_cookies.converter;

import com.sc.accounting_smart_cookies.dto.CategoryDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDTOConverter implements Converter<String , InvoiceDTO> {

    private final InvoiceService invoiceService;

    public InvoiceDTOConverter(@Lazy InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public InvoiceDTO convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }
        return invoiceService.findById(Long.parseLong(source));
    }

}
