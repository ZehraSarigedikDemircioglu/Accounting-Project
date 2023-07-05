package com.sc.accounting_smart_cookies.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;

    @Size(min = 2, max = 100, message = "Address should be 2-100 characters long.")
    @NotBlank(message = "Address is a required field.")
    private String addressLine1;

    @Size(max = 100, message = "Address should be 2-100 characters long.")
    private String addressLine2;

    @NotBlank(message = "City is a required field.")
    @Size(min = 2, max = 50, message = "City should have 2-50 characters long.")
    private String city;

    @NotBlank(message = "State is a required field.")
    @Size(min = 2, max = 50, message = "State should have 2-50 characters long.")
    @Column(nullable = false)
    private String state;

    @NotBlank(message = "Country is a required field.")
    private String country;

    @NotBlank(message = "Zipcode is a required field.")
    @Pattern(regexp = "\\d{5}([-]\\d{4})?$", message = "Zipcode should have a valid form")
    @Column(nullable = false)
    private String zipCode;
}
