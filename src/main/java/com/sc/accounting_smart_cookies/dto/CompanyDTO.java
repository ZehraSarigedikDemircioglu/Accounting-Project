package com.sc.accounting_smart_cookies.dto;

import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    private Long id;
    private String title;
    private String phone;
    private String website;
    //private AddressDto address;
    private CompanyStatus companyStatus;
}
