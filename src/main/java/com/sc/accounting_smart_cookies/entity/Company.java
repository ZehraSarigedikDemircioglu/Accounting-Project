package com.sc.accounting_smart_cookies.entity;

import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    @Column (unique = true)
    private String title;
    private String phone;
    private String website;

    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})// Cascading save operation
    private Address address;

}
