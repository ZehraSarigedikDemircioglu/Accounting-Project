package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


}
