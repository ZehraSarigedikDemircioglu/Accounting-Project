package com.sc.accounting_smart_cookies.entity;

import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Table(name = "invoices")
@Entity
public class Invoice extends BaseEntity {

    private String invoiceNo;
    private InvoiceStatus invoiceStatus;
    private InvoiceType invoiceType;
    private LocalDate date;

    @ManyToOne
    private ClientVendor clientVendor;
    @ManyToOne
    private Company company;
}