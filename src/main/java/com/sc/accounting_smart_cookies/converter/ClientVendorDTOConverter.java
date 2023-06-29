package com.sc.accounting_smart_cookies.converter;

import com.sc.accounting_smart_cookies.dto.ClientVendorDTO;
import com.sc.accounting_smart_cookies.service.ClientVendorService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClientVendorDTOConverter implements Converter<String, ClientVendorDTO> {

    ClientVendorService clientVendorService;

    public ClientVendorDTOConverter(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @Override
    public ClientVendorDTO convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }
        return
                clientVendorService.findById(Long.parseLong(source));
    }
}
