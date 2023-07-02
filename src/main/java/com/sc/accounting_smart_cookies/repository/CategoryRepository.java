package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Category;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Role;
import com.sc.accounting_smart_cookies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.company=?1 ORDER BY c.description asc ")
    List<Category> findAllByCompany (Company company);
}
