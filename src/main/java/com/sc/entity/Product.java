package com.sc.entity;

import com.sc.enums.ProductUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    private String name;
    private Integer quantityInStock;
    private Integer lowLimitAlert;
    private ProductUnit productUnit;
    @ManyToOne
    private Category category;
}
