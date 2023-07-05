package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.User;
import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.id=?1")
    Company getCompanyForCurrent(Long id);
    @Query("SELECT c FROM Company c where c.id!=?1")
    List<Company>getAllCompanyForRoot(Long id);

    boolean existsByTitle(String title);



}
