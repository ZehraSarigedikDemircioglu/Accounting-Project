package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.CompanyRepository;
import com.sc.accounting_smart_cookies.service.CompanyService;
import com.sc.accounting_smart_cookies.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }


    @Override
    public CompanyDTO findById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow();
        return mapperUtil.convert(company, new CompanyDTO());
    }

    @Override
    public List<CompanyDTO> listAllCompanies() {
        return companyRepository.findAll().stream().map(company -> mapperUtil.convert(company, new CompanyDTO())).collect(Collectors.toList());
    }

    @Override
    public CompanyDTO update(CompanyDTO companyDTO, Long id) {
        Company company = companyRepository.findById(id).orElseThrow();
        Company company1 = mapperUtil.convert(companyDTO, new Company());
        company1.setId(company.getId());
        company1.setCompanyStatus(company.getCompanyStatus());
        companyRepository.save(company1);
        return mapperUtil.convert(company1, new CompanyDTO());
    }

    @Override
    public void create (CompanyDTO companyDTO) {

        companyDTO.setCompanyStatus(CompanyStatus.PASSIVE);
        companyRepository.save(mapperUtil.convert(companyDTO, new Company()));

    }

    @Override
    public List<CompanyDTO> getAllCompaniesForCurrentUser() {
        UserDTO loggedInUser=securityService.getLoggedInUser();
       if (loggedInUser.getRole().getId()==1) {
           List<Company> companies = companyRepository.getAllCompanyForRoot(loggedInUser.getCompany().getId());
           return companies.stream().map(company -> mapperUtil.convert(company, new CompanyDTO()))
                   .collect(Collectors.toList());
       }else{
           Company company= companyRepository.getCompanyForCurrent(loggedInUser.getCompany().getId());
           return Collections.singletonList(mapperUtil.convert(company, new CompanyDTO()));
       }
    }

//    @Override
//    public void activateCompany(Long companyId) {
//       companyRepository.updateStatusById(companyId, CompanyStatus.ACTIVE);
//
//    }
//
//    @Override
//    public void deactivateCompany(Long companyId) {
//        companyRepository.updateStatusById(companyId, CompanyStatus.PASSIVE);
//    }


}
