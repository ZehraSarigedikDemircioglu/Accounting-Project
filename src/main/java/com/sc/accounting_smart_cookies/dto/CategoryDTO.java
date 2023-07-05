package com.sc.accounting_smart_cookies.dto;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private Long id;

    @NotBlank
    @NotNull
    @Size(max = 100, min = 2)
    private String description;

    private CompanyDTO company;

    private boolean hasProduct;



}
