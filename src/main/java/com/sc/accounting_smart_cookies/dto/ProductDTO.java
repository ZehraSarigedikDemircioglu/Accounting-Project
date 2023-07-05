package com.sc.accounting_smart_cookies.dto;

import com.sc.accounting_smart_cookies.enums.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    @NotBlank(message = "Product Name is required field.")
    @Size(max = 100, min = 2, message = "Product Name  must be between 2 and 100 characters long.")
    private String name;
    private Integer quantityInStock;
    @NotNull(message = "Low Limit Alert is a required field.")
    @Range(min = 1, message = "Low Limit Alert should be at least 1.")
    private Integer lowLimitAlert;
    @Valid
    @NotNull(message =  "Product Unit is a required field.")
    private ProductUnit productUnit;
    @Valid
    @NotNull(message =  "Category is a required field.")
    private CategoryDTO category;
}
