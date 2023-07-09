package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.enums.InvoiceType;

import java.util.List;

public interface InvoiceProductService {

    List<InvoiceProductDTO> findAllByInvoiceId(Long invoiceId);

    InvoiceProductDTO findById(Long id);

    void save(InvoiceProductDTO invoiceProductDTO, Long id);

    void deleteById(Long invoiceProductId);

    void completeApproval(Long invoiceId, InvoiceType type);

    boolean insufficientQuantity(InvoiceProductDTO invoiceProductDTO);
}
