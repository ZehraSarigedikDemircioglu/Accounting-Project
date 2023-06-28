package com.sc.accounting_smart_cookies.service.impl;

import com.sc.accounting_smart_cookies.converter.InvoiceProductDTOConverter;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.mapper.InvoiceProductMapper;
import com.sc.accounting_smart_cookies.repository.InvoiceProductRepository;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceProductMapper invoiceProductMapper;
    private final InvoiceProductDTOConverter invoiceProductDTOConverter;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, InvoiceProductMapper invoiceProductMapper, @Lazy InvoiceProductDTOConverter invoiceProductDTOConverter) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.invoiceProductMapper = invoiceProductMapper;
        this.invoiceProductDTOConverter = invoiceProductDTOConverter;
    }

    @Override
    public List<InvoiceProductDTO> findAll() {

        List<InvoiceProduct> invoices = invoiceProductRepository.findAll();

        return invoices.stream().map(invoiceProductMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceProductDTO findById(Long id) {

//        Optional<InvoiceProduct> invoiceProduct = invoiceProductRepository.findById(id);

        return invoiceProductDTOConverter.convert(id);
    }
}
