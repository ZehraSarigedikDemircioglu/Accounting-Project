package com.sc.accounting_smart_cookies.entity;

import com.sc.accounting_smart_cookies.enums.ProductUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    private String name;
    private Integer quantityInStock;
    private Integer lowLimitAlert;

    @Enumerated(EnumType.STRING)
    private ProductUnit productUnit;
    @ManyToOne
    private Category category;
}
