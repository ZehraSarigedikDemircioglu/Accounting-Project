package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
