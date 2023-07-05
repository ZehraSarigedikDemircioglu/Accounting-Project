package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.ClientVendorDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.entity.ClientVendor;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.ClientVendorRepository;
import com.sc.accounting_smart_cookies.service.ClientVendorService;
import com.sc.accounting_smart_cookies.service.CompanyService;
import com.sc.accounting_smart_cookies.service.SecurityService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;
    SecurityService securityService;
    CompanyService companyService;
    ClientVendorDTO clientVendor;



    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
    }



    @Override
    public ClientVendorDTO findById(Long id) {
        ClientVendor clientVendor= clientVendorRepository.findById(id).orElseThrow();
        return mapperUtil.convert(clientVendor, new ClientVendorDTO());
    }


    @Override
    public List<ClientVendorDTO> findAll() {
        List<ClientVendor> clientVendors = clientVendorRepository.getClientVendorsSortedByTitleAndName();
        return clientVendors.stream().map(clientVendor1 -> mapperUtil.convert(clientVendor1, new ClientVendorDTO())).collect(Collectors.toList());
    }


    @Override
    public ClientVendorDTO save(ClientVendorDTO clientVendorDTO) {
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());
        clientVendorRepository.save(clientVendor);
        return mapperUtil.convert(clientVendor, new ClientVendorDTO());
    }


    @Override
    public ClientVendorDTO update(Long id, ClientVendorDTO clientVendorDTO) {
        ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow();
        ClientVendor newClientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());
        newClientVendor.setId(clientVendor.getId());
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

        List<ClientVendor> vendorList = clientVendorRepository.findAllByClientVendorType(clientVendorType);

        return vendorList.stream().map(vendor -> mapperUtil.convert(vendor, new ClientVendorDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientVendorDTO> getListOfClientVendors(ClientVendorDTO clientVendor) {
        return null;
    }


//    @Override
//    public List<ClientVendorDTO> getListOfClientVendors() {
//        if (securityService.getLoggedInUser().getRole().getDescription().equals("Root User")) {
//
//            return clientVendorRepository.getClientVendorsSortedByTitleAndName()
//                    .stream()
//                    .map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDTO()))
//                    .collect(Collectors.toList());
//        } else {
//            return clientVendorRepository.findAllByCompanyTitleAndSortByTypeAndName(
//                            companyService.getCompanyDTOByLoggedInUser().getTitle()).stream()
//                    .map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDTO()))
//                    .collect(Collectors.toList());
//        }

    }


