package com.sc.accounting_smart_cookies.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceProductDTO {

   private Long id;

   private Integer quantity;
   private BigDecimal price;
   private Integer tax;
   private BigDecimal total;

   private BigDecimal profitLoss;
   private Integer remainingQty;
   private InvoiceDTO invoice;
   private ProductDTO product;
}
