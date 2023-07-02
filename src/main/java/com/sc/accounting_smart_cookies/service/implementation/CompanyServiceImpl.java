package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.CompanyRepository;
import com.sc.accounting_smart_cookies.service.CompanyService;
import com.sc.accounting_smart_cookies.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, SecurityService securityService, MapperUtil mapperUtil) {
        this.companyRepository = companyRepository;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public CompanyDTO findById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow();
        return mapperUtil.convert(company, new CompanyDTO());
    }

    @Override
    public List<CompanyDTO> listAllCompanies() {
        List <Company> companies = companyRepository.findAll();
        companies = companies.stream().filter(company -> company.getId() !=1).collect(Collectors.toList());
        companies.sort(Comparator.comparing(company -> company.getCompanyStatus()==CompanyStatus.ACTIVE ? 0:1));
        return companies.stream().map(company -> mapperUtil.convert(company, new CompanyDTO())).collect(Collectors.toList());
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
    public CompanyDTO getCompanyOfLoggedInUser() {
        return securityService.getLoggedInUser().getCompany();
    }

    @Override
    public CompanyDTO activateCompany(Long companyId) {
        Company company= companyRepository.findById(companyId).orElseThrow();
         company.setCompanyStatus(CompanyStatus.ACTIVE);
         companyRepository.save(company);
         return mapperUtil.convert(company, new CompanyDTO());
    }

    @Override
    public CompanyDTO deactivateCompany(Long companyId) {
        Company company= companyRepository.findById(companyId).orElseThrow();
        company.setCompanyStatus(CompanyStatus.PASSIVE);
        companyRepository.save(company);
        return mapperUtil.convert(company, new CompanyDTO());
    }


}
