package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAll();
    List<Invoice> findAllByInvoiceType(InvoiceType invoiceType);

    Invoice findTopByOrderByInvoiceNoDesc();

}
