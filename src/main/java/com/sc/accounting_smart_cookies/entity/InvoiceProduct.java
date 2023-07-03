package com.sc.accounting_smart_cookies.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice_products")
@Entity
@Where(clause = "is_deleted=false")
public class InvoiceProduct extends BaseEntity {

    private int quantity;
    private BigDecimal price;
    private int tax;
    private BigDecimal profitLoss;
    private Integer remainingQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
