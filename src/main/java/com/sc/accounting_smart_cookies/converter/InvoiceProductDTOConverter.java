package com.sc.accounting_smart_cookies.converter;

import org.springframework.core.convert.converter.Converter;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class InvoiceProductDTOConverter implements Converter<String, InvoiceProductDTO> {
    private final InvoiceProductService invoiceProductService;

    public InvoiceProductDTOConverter(@Lazy InvoiceProductService invoiceProductService) {

        this.invoiceProductService = invoiceProductService;
    }

    @SneakyThrows
    @Override
    public InvoiceProductDTO convert(String source){
        if (source == null || source.equals("")) {
            return null;
        }
        return invoiceProductService.findById(Long.parseLong(source));
    }

}