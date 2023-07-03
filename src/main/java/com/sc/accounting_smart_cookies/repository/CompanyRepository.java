package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

//    List<Company> getByCompanyStatusOrderByCompanyStatusAsc();

      @Query (value = "select * from Company where id!=1", nativeQuery= true)
      List <Company> getCompaniesById();

}
