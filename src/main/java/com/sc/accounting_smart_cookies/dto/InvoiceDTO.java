package com.sc.accounting_smart_cookies.dto;

import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDTO {

    private Long id;
    private String invoiceNo;
    private InvoiceStatus invoiceStatus;
    private InvoiceType invoiceType;
    private LocalDate date;
    @Valid
    private CompanyDTO company;
    @Valid
    @NotNull(message = "Its cant be null")
    private ClientVendorDTO clientVendor;
    @NotBlank(message = "Price is required field")
    @Range(min = 0, message = " Limit should be at least 1.")
    private BigDecimal price;
    @NotBlank(message = "tax is required field")
    @Range(min =0, message = "Low Limit Alert should be at least 1.")
    private Integer tax;
    @NotBlank(message = "total is required field")
    private BigDecimal total;
    @Valid
    private List<InvoiceProductDTO> invoiceProducts;
}