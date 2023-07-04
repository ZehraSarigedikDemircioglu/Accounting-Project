package com.sc.accounting_smart_cookies.dto;

import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {

    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title is a required field.")
    @Size(min = 2, max = 100, message = "Title should be 2-100 characters long.")
    private String title;

    @Pattern(regexp = "^\\+1\\s?\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{4}$")
    private String phone;

    @NotBlank(message = "Website is a required field.")
    @Pattern(regexp = "^http(s{0,1})://[a-zA-Z0-9/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*", message = "Website should have a valid format.")
    private String website;


    private AddressDTO address;

    private CompanyStatus companyStatus;
}
