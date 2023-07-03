package com.sc.accounting_smart_cookies.dto;

import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

    private Long id;
    private String invoiceNo;
    private InvoiceStatus invoiceStatus;
    private InvoiceType invoiceType;
    private LocalDate date;
    private CompanyDTO company;
    private ClientVendorDTO clientVendor;
    private BigDecimal price;
    private Integer tax;
    private BigDecimal total;
    private List<InvoiceProductDTO> invoiceProducts;
}