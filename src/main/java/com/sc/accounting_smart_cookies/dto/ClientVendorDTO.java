package com.sc.accounting_smart_cookies.dto;

import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientVendorDTO{

    private Long id;
    @NotBlank(message = "Client/Vendor Name is a required field.")
    @Size(min = 2, max = 50, message ="Client/Vendor Name should be between 2 and 50 characters long.")
    private String clientVendorName;
    @NotNull(message = "Please select type")
    private ClientVendorType clientVendorType;
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" // +111 (202) 555-0125  +1 (202) 555-0125
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"                                  // +111 123 456 789
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", message = "Phone Number is required field and may be in any valid phone number format.")
    private String phone;
    @Pattern(regexp = "^http(s{0,1})://[a-zA-Z0-9/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*"
            ,message = "Website should have a valid format.")
    private String website;
    @Valid
    private AddressDTO address;
    @Valid
    private CompanyDTO company;

}
