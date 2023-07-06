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
   @NotBlank(message = "Price is required field")
   @Range(min = 0, message = "Low Limit Alert should be at least 1.")
   private BigDecimal price;
   @NotBlank
   private Integer tax;
   @NotBlank
   private BigDecimal total;
   @NotBlank
   private BigDecimal profitLoss;
   private Integer remainingQty;
   private InvoiceDTO invoice;
   private ProductDTO product;
}
