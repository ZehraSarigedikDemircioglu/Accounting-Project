package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.enums.InvoiceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {

    List<InvoiceDTO> findAll();
    List<InvoiceDTO> findInvoicesByType(InvoiceType invoiceType);

    InvoiceDTO findById(Long id);

    InvoiceDTO getNewInvoice(InvoiceType invoiceType);

    void deleteById(Long id);

    InvoiceDTO save(InvoiceDTO invoiceDTO, InvoiceType invoiceType);

    void approveInvoiceById(Long id);

    InvoiceDTO update(Long id, InvoiceDTO invoiceDTO);

    List<InvoiceDTO> findTop3ByCompanyOrderByDateDesc();

    InvoiceDTO printInvoice(Long id);

    boolean existsByClientVendorId(Long id);

}
