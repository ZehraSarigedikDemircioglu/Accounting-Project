package com.sc.accounting_smart_cookies.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice_product")
@Entity
public class InvoiceProduct extends BaseEntity {

   private int quantity;
   private BigDecimal price;
    private int tax;
  private BigDecimal profitLoss;
    private int remainingQty;
//@ManyToOne
//    Invoice invoice;

    //@ManyToOne
//Product product;
}
