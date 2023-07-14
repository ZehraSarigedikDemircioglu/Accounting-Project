package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.entity.Product;
import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

    List<InvoiceProduct> findAllByInvoice_Id(Long invoiceId);

    List<InvoiceProduct> findInvoiceProductsByInvoiceInvoiceTypeAndProductAndRemainingQuantityNotOrderByIdAsc
            (InvoiceType invoiceType, Product product, Integer remainingQuantity);

    List<InvoiceProduct> findAllByInvoiceInvoiceStatusAndInvoiceInvoiceTypeAndInvoiceCompanyTitle(
            InvoiceStatus status, InvoiceType type, String title);

    List<InvoiceProduct> findAllByInvoiceInvoiceStatusAndInvoiceCompanyTitleOrderByInvoiceLastUpdateDateTimeDesc(
            InvoiceStatus status, String title);

    List<InvoiceProduct> findAllByInvoiceInvoiceStatusAndInvoiceInvoiceTypeAndInvoiceCompany(InvoiceStatus status, InvoiceType type, Company company);

    List<InvoiceProduct> findAllInvoiceProductByProductId(Long id);

    List<InvoiceProduct>findAllByInvoiceCompanyTitle(String title);
}
