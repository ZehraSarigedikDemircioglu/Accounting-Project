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

    @NotBlank(message = "Category description is a required field.")
    @NotNull
    @Size(max = 100, min = 2, message ="Category Name should be between 2 and 50 characters long.")
    private String description;

    private CompanyDTO company;

    private boolean hasProduct;



}
