package com.sc.accounting_smart_cookies.entity;

import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    private String title;
    private String phone;
    private String website;
    private CompanyStatus companyStatus;
    private Address address;

}
