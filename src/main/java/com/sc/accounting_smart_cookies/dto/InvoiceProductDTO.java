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
    @Range(min = 1, max = 100, message = "Quantity cannot be greater than 100 or less than 1")
    private Integer quantity;
    @NotNull(message = "Price is a required field.")
    @Range(min = 1, message = "Price should be at least $1")
    private BigDecimal price;
    @NotNull(message = "Tax is a required field.")
    @Range(min = 0, max = 20, message = "Tax should be between 0% and 20%")
    private Integer tax;
    private BigDecimal total;

    private BigDecimal profitLoss;
    private Integer remainingQty;
    private InvoiceDTO invoice;
    @Valid
    @NotNull(message = "Product is a required field.")
    private ProductDTO product;
}
