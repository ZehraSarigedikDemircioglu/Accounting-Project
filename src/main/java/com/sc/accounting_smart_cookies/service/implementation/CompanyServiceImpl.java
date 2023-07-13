package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.client.CountryClient;
import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.dto.countries.CountryDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import com.sc.accounting_smart_cookies.exceptions.CompanyNotFoundException;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.CompanyRepository;
import com.sc.accounting_smart_cookies.service.CompanyService;
import com.sc.accounting_smart_cookies.service.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;


    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil, SecurityService securityService, CountryClient countryClient) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }


    @Override
    public CompanyDTO findById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("This Company can not be found in the system"));
        return mapperUtil.convert(company, new CompanyDTO());
    }

    @Override
    public List<CompanyDTO> listAllCompanies() {
        List <Company> companies = companyRepository.findAll();
        companies = companies.stream().filter(company -> company.getId() !=1).collect(Collectors.toList());
        companies.sort(Comparator.comparing(company -> company.getCompanyStatus()==CompanyStatus.ACTIVE ? 0 :1));
        return companies.stream().map(company -> mapperUtil.convert(company, new CompanyDTO())).collect(Collectors.toList());
    }

    @Override
    public CompanyDTO update(CompanyDTO companyDTO, Long id) {

            Company company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("This Company with id number " + id + " can not be found in the system." ));
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

    @Override
    public void activateCompany(Long companyId) {

        Company company = companyRepository.findById(companyId).get();
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        companyRepository.save(company);
        mapperUtil.convert(company, new CompanyDTO());

    }

    @Override
    public void deactivateCompany(Long companyId) {
        Company company = companyRepository.findById(companyId).get();
        company.setCompanyStatus(CompanyStatus.PASSIVE);
        companyRepository.save(company);
        securityService.getLoggedInUser();
        mapperUtil.convert(company, new CompanyDTO());
    }

    @Override
    public CompanyDTO getCompanyOfLoggedInUser() {
        return   securityService.getLoggedInUser().getCompany();

    }




}
