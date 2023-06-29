package com.sc.accounting_smart_cookies.entity;

import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name= "clients_vendors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted=false")
public class ClientVendor extends BaseEntity{

    private String clientVendorName;
    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;
    private String phone;
    private String website;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;


}
