package com.sc.accounting_smart_cookies.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice_products")
@Entity
public class InvoiceProduct extends BaseEntity {

    private int quantity;
    private BigDecimal price;
    private int tax;
    private BigDecimal profitLoss;
    private int remainingQty;
    @ManyToOne(fetch = FetchType.LAZY)
    Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    Product product;
}
