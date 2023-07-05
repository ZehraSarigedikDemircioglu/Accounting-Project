package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.ClientVendorDTO;
import com.sc.accounting_smart_cookies.entity.ClientVendor;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.ClientVendorRepository;
import com.sc.accounting_smart_cookies.service.ClientVendorService;
import com.sc.accounting_smart_cookies.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;

    private final CompanyService companyService;


    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil, CompanyService companyService) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
    }



    @Override
    public ClientVendorDTO findById(Long id) {
        ClientVendor clientVendor= clientVendorRepository.findById(id).orElseThrow();
        return mapperUtil.convert(clientVendor, new ClientVendorDTO());
    }


    @Override
    public List<ClientVendorDTO> findAll() {
        Company company = mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company());
        return clientVendorRepository
                .findAllByCompany(company).stream()
                .sorted(Comparator.comparing(ClientVendor::getClientVendorType)
                        .reversed()
                        .thenComparing(ClientVendor::getClientVendorName))
                .map(cv ->  mapperUtil.convert(cv, new ClientVendorDTO()))
                .collect(Collectors.toList());
    }


    @Override
    public ClientVendorDTO save(ClientVendorDTO clientVendorDTO) {
        Company company = mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company());
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());
        clientVendor.setCompany(company);
        clientVendorRepository.save(clientVendor);
        return mapperUtil.convert(clientVendor, new ClientVendorDTO());
    }


    @Override
    public ClientVendorDTO update(Long id, ClientVendorDTO clientVendorDTO) {
        Company company = mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company());
        ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow();
        ClientVendor newClientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());
        newClientVendor.setId(clientVendor.getId());
        newClientVendor.setCompany(company);
        clientVendorRepository.save(newClientVendor);
        return mapperUtil.convert(newClientVendor, new ClientVendorDTO());
    }



    @Override
    public void deleteById(Long id) {
        Optional<ClientVendor> clientVendor = clientVendorRepository.findById(id);
        if(clientVendor.isPresent()){
            clientVendor.get().setIsDeleted(true);
            clientVendorRepository.save(clientVendor.get());
        }
    }

    @Override
    public List<ClientVendorDTO> findVendorsByType(ClientVendorType clientVendorType) {

//        List<ClientVendor> vendorList = clientVendorRepository.findAllByClientVendorType(clientVendorType);

        return findAll().stream().filter(clientVendorDTO ->
                        clientVendorDTO.getClientVendorType().equals(clientVendorType))
                .collect(Collectors.toList());
    }


}
