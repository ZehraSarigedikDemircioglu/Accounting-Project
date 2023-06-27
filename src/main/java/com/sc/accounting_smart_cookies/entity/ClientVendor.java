package com.sc.accounting_smart_cookies.entity;

import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name= "clients_vendors")
@Getter
@Setter
@NoArgsConstructor
public class ClientVendor extends BaseEntity{

    private String clientVendorName;
    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;
    private String phone;
    private String website;

    @OneToOne
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;


}
