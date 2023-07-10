package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.ClientVendorDTO;
import com.sc.accounting_smart_cookies.enums.ClientVendorType;

import java.util.List;


public interface ClientVendorService {

    ClientVendorDTO findById(Long id);

    List<ClientVendorDTO> findAll();

    ClientVendorDTO save(ClientVendorDTO clientVendorDTO);

    ClientVendorDTO update(Long id, ClientVendorDTO clientVendorDTO);

    void deleteById(Long id);

   List<ClientVendorDTO> findVendorsByType(ClientVendorType clientVendorType);

    boolean isClientVendorByCompanyNameExist(ClientVendorDTO clientVendorDTO);

    boolean isClientVendorHasInvoice(Long id);
}
