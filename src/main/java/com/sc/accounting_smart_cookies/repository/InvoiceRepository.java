package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.ClientVendor;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.entity.Payment;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAll();
    List<Invoice> findAllByInvoiceType(InvoiceType invoiceType);

    List<Invoice> findInvoicesByCompanyAndInvoiceType(Company company, InvoiceType invoiceType);
    List<Invoice> findTop3ByCompanyOrderByDateDesc(Company company);

    boolean existsByCompanyAndClientVendorId(Company company, Long id);


}
