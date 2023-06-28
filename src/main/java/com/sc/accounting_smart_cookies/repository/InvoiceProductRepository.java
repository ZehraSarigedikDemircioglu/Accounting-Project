package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {
}
