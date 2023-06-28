package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;

import java.util.List;

public interface InvoiceService {

    List<InvoiceDTO> findAll();

    InvoiceDTO findById(Long id);

}
