package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {

    CompanyDTO findById(Long id);
    List<CompanyDTO> listAllCompanies ();
    CompanyDTO update (CompanyDTO companyDTO, Long id);
    void create (CompanyDTO companyDTO);
    List<CompanyDTO> getAllCompaniesForCurrentUser();
    void activateCompany (Long companyId);
    void deactivateCompany (Long companyId);

    CompanyDTO getCompanyOfLoggedInUser();

}
