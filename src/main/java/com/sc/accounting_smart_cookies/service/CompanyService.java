package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.entity.Company;

import java.util.List;

public interface CompanyService {

    CompanyDTO findById(Long id);
    List<CompanyDTO> listAllCompanies ();
    CompanyDTO update (CompanyDTO companyDTO, Long id);
    void create (CompanyDTO companyDTO);
    CompanyDTO getCompanyOfLoggedInUser();
    CompanyDTO activateCompany(Long companyId);
    CompanyDTO deactivateCompany (Long companyId);


//    List<CompanyDTO> getSortedCompanies();


}
