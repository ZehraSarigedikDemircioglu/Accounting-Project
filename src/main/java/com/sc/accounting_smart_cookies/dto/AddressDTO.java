package com.sc.accounting_smart_cookies.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;
    @NotBlank(message = "Address is a required field.")
    @Size(min = 2, max = 100, message = "Address should have 2-100 characters.")
    private String addressLine1;
    @Size(max = 100, message = "Address should have a maximum of 100 characters.")
    private String addressLine2;
    @NotBlank(message = "City is a required field.")
    @Size(min = 2, max = 50, message = "City should have 2-50 characters.")
    private String city;
    @NotBlank(message = "State is a required field.")
    @Size(min = 2, max = 50, message = "State should have 2-50 characters.")
    private String state;
    @NotNull(message = "Country is a required field.")
    @Size(min = 2, max = 50, message = "Country should have 2-50 characters.")
    private String country;
    @NotBlank(message = "Zip Code is a required field.")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Zip Code should have a valid format.")
    private String zipCode;
}
