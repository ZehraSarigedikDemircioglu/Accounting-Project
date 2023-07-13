package com.sc.accounting_smart_cookies.entity;

import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "invoices")
@Entity
@Where(clause = "is_deleted=false")
public class Invoice extends BaseEntity {

    private String invoiceNo;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;
    private LocalDate date;

    @ManyToOne
    private ClientVendor clientVendor;
    @ManyToOne
    private Company company;




}