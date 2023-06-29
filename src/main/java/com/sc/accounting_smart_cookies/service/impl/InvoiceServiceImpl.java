package com.sc.accounting_smart_cookies.service.impl;

import com.sc.accounting_smart_cookies.converter.InvoiceDTOConverter;
import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.InvoiceRepository;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceDTOConverter invoiceDTOConverter;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, @Lazy InvoiceDTOConverter invoiceDTOConverter) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceDTOConverter = invoiceDTOConverter;
    }

    @Override
    public List<InvoiceDTO> findAll() {

        List<Invoice> invoices = invoiceRepository.findAllByInvoiceType(InvoiceType.PURCHASE);

        return invoices.stream().map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> findInvoicesByType(InvoiceType invoiceType) {

        List<Invoice> invoices = invoiceRepository.findAllByInvoiceType(invoiceType);

        return invoices.stream().map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO findById(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow();

        return mapperUtil.convert(invoice, new InvoiceDTO());
    }

    @Override
    public InvoiceDTO getNewInvoice() {

        InvoiceDTO newInvoice = new InvoiceDTO();
        newInvoice.setDate(LocalDate.now());
        newInvoice.setInvoiceNo("P" + getNextInvoiceNo().substring(1));

        return newInvoice;
    }

    private String getNextInvoiceNo() {

        return (invoiceRepository.findTopByOrderByInvoiceNoDesc()).getInvoiceNo();
    }

}
