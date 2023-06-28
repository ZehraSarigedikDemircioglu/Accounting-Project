package com.sc.accounting_smart_cookies.service.impl;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.mapper.InvoiceProductMapper;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.InvoiceProductRepository;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceProductMapper invoiceProductMapper;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, InvoiceProductMapper invoiceProductMapper) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.invoiceProductMapper = invoiceProductMapper;
    }

    @Override
    public List<InvoiceProductDTO> findAll() {

        List<InvoiceProduct> invoices = invoiceProductRepository.findAll();

        return invoices.stream().map(invoiceProductMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceProductDTO findById(Long id) {

        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id);

        return null;
    }
}
