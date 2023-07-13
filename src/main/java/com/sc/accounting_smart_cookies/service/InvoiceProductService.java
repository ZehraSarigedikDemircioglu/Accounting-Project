package com.sc.accounting_smart_cookies.service;


import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceProductService {

    List<InvoiceProductDTO> listAll();

    List<InvoiceProductDTO> findAllByInvoiceId(Long invoiceId);

    InvoiceProductDTO findById(Long id);

    void save(InvoiceProductDTO invoiceProductDTO, Long id);

    void deleteById(Long invoiceProductId);

    void completeApproval(Long invoiceId, InvoiceType type);

    boolean insufficientQuantity(InvoiceProductDTO invoiceProductDTO);

    BigDecimal setProfitLossOfInvoiceProductsForSalesInvoice(InvoiceProduct toBeSoldProduct);

    List<InvoiceProductDTO> getAllProductWithStatusTypeAndCompanyTitle(
            InvoiceStatus status, InvoiceType type, String title);

    List<InvoiceProductDTO> findAllInvoicesByStatusApproved ( InvoiceStatus status, String company);

    List<InvoiceProductDTO> findAllByInvoiceStatusAndInvoiceTypeAndCompany(InvoiceStatus status, InvoiceType type);

    List<InvoiceProductDTO> findAllInvoiceProductsByProductId(Long id);
}
