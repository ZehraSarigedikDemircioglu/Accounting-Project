package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.enums.InvoiceType;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {

    List<InvoiceDTO> findAll();
    List<InvoiceDTO> findInvoicesByType(InvoiceType invoiceType);

    InvoiceDTO findById(Long id);

    InvoiceDTO getNewInvoice();

    void deleteById(Long id);
}
