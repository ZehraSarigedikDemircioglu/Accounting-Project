package com.sc.accounting_smart_cookies.dto;

import com.sc.accounting_smart_cookies.enums.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    @NotBlank
    @Size(max = 100, min = 2)
    private String name;
    private Integer quantityInStock;
    @NotBlank
    @Size(min = 1)
    private Integer lowLimitAlert;
    @NotBlank
    private ProductUnit productUnit;
    @NotBlank
    private CategoryDTO category;

}
