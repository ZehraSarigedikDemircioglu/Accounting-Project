package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;

import java.util.List;

public interface InvoiceProductService {

    List<InvoiceProductDTO> findAll();

    InvoiceProductDTO findById(Long id);
}
