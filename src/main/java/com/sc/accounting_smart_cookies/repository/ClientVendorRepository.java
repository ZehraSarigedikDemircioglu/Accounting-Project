package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.entity.ClientVendor;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {

    List<ClientVendor> findAllByClientVendorType(ClientVendorType clientVendorType);

    @Query(value = "SELECT c FROM ClientVendor c ORDER BY c.clientVendorType asc, c.clientVendorName asc")
    List<ClientVendor> getClientVendorsSortedByTitleAndName();

    List<ClientVendor> findAllByCompany(Company company);

   ClientVendor findByClientVendorNameAndCompany (String clientVendorName, Company company);


}
