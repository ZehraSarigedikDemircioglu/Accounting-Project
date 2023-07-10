package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.entity.Product;
import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Queue;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

    List<InvoiceProduct> findAllByInvoice_Id(Long invoiceId);

    List<InvoiceProduct> findAllByProductIdAndInvoiceCompanyTitleAndInvoice_InvoiceTypeAndInvoiceInvoiceStatusOrderByInvoiceDateAsc
            (Long id, String title, InvoiceType type,InvoiceStatus status);
//    @Query("select i from InvoiceProduct i where i.product.company.title=?1 and i.invoice.invoiceType=?2 and i.invoice.invoiceStatus=?3 and i.remainingQuantity>=?4")
//    List<InvoiceProduct> getSomeAllByInvoiceCompanyTitleAndInvoice_InvoiceTypeAndInvoiceInvoiceStatusAndRemainingQuantityGreaterThanOrderByInvoiceDateAsc(
//            String title, InvoiceType type, InvoiceStatus status, Integer number);
List<InvoiceProduct> findInvoiceProductsByInvoiceInvoiceTypeAndProductAndRemainingQuantityNotOrderByIdAsc(InvoiceType invoiceType, Product product, Integer remainingQuantity);

    List<InvoiceProduct> findAllByInvoiceInvoiceStatusAndInvoiceInvoiceTypeAndInvoiceCompanyTitle
            (InvoiceStatus status, InvoiceType type, String title);

    @Query("SELECT ip FROM InvoiceProduct ip WHERE ip.insertDateTime BETWEEN :startDate AND :endDate AND ip.invoice.invoiceType = :status")
    List<InvoiceProduct> findByDateRangeAndStatus(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("status") InvoiceType type);

}
