package com.sc.accounting_smart_cookies.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceProductDTO {

   private Long id;
   @NotNull(message = "Quantity is a required field.")
   private Integer quantity;
   @NotNull(message = "Price is a required field.")
   private BigDecimal price;
   @NotNull(message = "Tax is a required field.")
   private Integer tax;
   private BigDecimal total;

   private BigDecimal profitLoss;
   private Integer remainingQty;
   private InvoiceDTO invoice;
   @NotNull(message = "Product is a required field.")
   private ProductDTO product;
}
