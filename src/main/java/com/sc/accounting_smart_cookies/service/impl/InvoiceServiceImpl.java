package com.sc.accounting_smart_cookies.service.impl;

import com.sc.accounting_smart_cookies.converter.InvoiceDTOConverter;
import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.mapper.InvoiceMapper;
import com.sc.accounting_smart_cookies.repository.InvoiceRepository;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceDTOConverter invoiceDTOConverter;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, @Lazy InvoiceDTOConverter invoiceDTOConverter) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.invoiceDTOConverter = invoiceDTOConverter;
    }

    @Override
    public List<InvoiceDTO> findAll() {

        List<Invoice> invoices = invoiceRepository.findAllByInvoiceType(InvoiceType.PURCHASE);

        return invoices.stream().map(invoiceMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> findAllPurchaseInvoices() {

        List<Invoice> invoices = invoiceRepository.findAllByInvoiceType(InvoiceType.PURCHASE);

        return invoices.stream().map(invoiceMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> findAllSalesInvoices() {

        List<Invoice> invoices = invoiceRepository.findAllByInvoiceType(InvoiceType.SALES);

        return invoices.stream().map(invoiceMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO findById(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow();

        return invoiceMapper.convertToDto(invoice);
    }
}
