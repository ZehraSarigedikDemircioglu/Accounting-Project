package com.sc.accounting_smart_cookies.dto;

import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientVendorDTO{

    private Long id;
    private String clientVendorName;
    private ClientVendorType clientVendorType;
    private String phone;
    private String website;
    private AddressDTO address;
    private CompanyDTO company;

}
