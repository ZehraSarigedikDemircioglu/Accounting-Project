package com.sc.accounting_smart_cookies.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="addresses")
@NoArgsConstructor
@Getter
@Setter
public class Address extends BaseEntity{


    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}