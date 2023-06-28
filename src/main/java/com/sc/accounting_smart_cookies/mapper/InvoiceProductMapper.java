package com.sc.accounting_smart_cookies.mapper;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class InvoiceProductMapper {

    private final ModelMapper modelMapper;

    public InvoiceProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public InvoiceProduct convertToEntity(InvoiceProductDTO dto) {
        return modelMapper.map(dto, InvoiceProduct.class);
    }

    public InvoiceProductDTO convertToDto(InvoiceProduct entity) {
        return modelMapper.map(entity, InvoiceProductDTO.class);
    }

}
