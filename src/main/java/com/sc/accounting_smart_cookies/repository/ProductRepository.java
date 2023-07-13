package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Category;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

        List<Product> findAllByCompany(Company company);
        Product findByNameAndCategoryCompany(String productName, Company company);

        List<Product> findAllByCategory(Category category);

    List<Product> findByCategory(Category category);

}
