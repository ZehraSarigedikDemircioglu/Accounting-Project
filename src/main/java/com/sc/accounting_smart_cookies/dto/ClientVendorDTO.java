package com.sc.accounting_smart_cookies.dto;

import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientVendorDTO{

    private Long id;
    private String clientVendorName;
    private ClientVendorType clientVendorType;
    private String phone;
    private String website;
    private AddressDTO address;
    private CompanyDTO company;

}
