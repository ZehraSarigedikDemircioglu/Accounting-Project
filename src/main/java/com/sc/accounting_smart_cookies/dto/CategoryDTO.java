package com.sc.accounting_smart_cookies.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    private String description;

   // private CompanyDTO company;

    private boolean hasProduct;


}
