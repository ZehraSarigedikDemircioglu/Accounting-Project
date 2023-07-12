package com.sc.accounting_smart_cookies.dto;


import com.sc.accounting_smart_cookies.enums.Months;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDto {
    private Long id;
    private CompanyDTO company;
    private Months month;
    private int amount;
    private boolean isPaid;
    private LocalDate year;
    private LocalDate paymentDate;
}
