package com.sc.accounting_smart_cookies.mapper;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.entity.Invoice;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    private final ModelMapper modelMapper;

    public InvoiceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Invoice convertToEntity(InvoiceDTO dto) {
        return modelMapper.map(dto, Invoice.class);
    }

    public InvoiceDTO convertToDto(Invoice entity) {
        return modelMapper.map(entity, InvoiceDTO.class);
    }
}
