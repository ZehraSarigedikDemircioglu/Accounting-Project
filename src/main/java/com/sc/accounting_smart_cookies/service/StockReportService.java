package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.enums.InvoiceStatus;

import java.util.List;

public interface StockReportService {

    List <InvoiceProductDTO> generateStockReport();


}
