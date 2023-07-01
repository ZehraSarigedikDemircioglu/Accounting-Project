package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

//    @Query("SELECT i FROM Invoice i WHERE i.company.id=?1")
//    Company findCompanyByInvoice_Id(Long invoiceId);

//    Company findByInvoice
}
