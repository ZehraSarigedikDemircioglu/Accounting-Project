package com.sc.accounting_smart_cookies.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private Long id;

    private String description;

    private CompanyDTO company;

    private boolean hasProduct;


}
