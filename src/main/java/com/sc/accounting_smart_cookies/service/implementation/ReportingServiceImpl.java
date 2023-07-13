package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import com.sc.accounting_smart_cookies.service.ReportingService;
import com.sc.accounting_smart_cookies.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class ReportingServiceImpl implements ReportingService {
        private final SecurityService securityService;
        private final InvoiceProductService invoiceProductService;


    @Override
    public Map<String, BigDecimal> listMonthlyProfitLoss() {

        List<InvoiceProductDTO> salesList = invoiceProductService.getAllProductWithStatusTypeAndCompanyTitle(
                InvoiceStatus.APPROVED, InvoiceType.SALES, securityService.getLoggedInUser().getCompany().getTitle());

        Map<String, BigDecimal> monthlyProfitLoss = new HashMap<>();

        salesList.forEach(invoiceProduct -> {
            String year = String.valueOf(invoiceProduct.getInvoice().getDate().getYear());
            String month = invoiceProduct.getInvoice().getDate().getMonth().toString();
            BigDecimal profitLoss = invoiceProduct.getProfitLoss();
            BigDecimal totalProfitloss = monthlyProfitLoss.getOrDefault(year + " " + month, BigDecimal.ZERO).add(profitLoss);
            monthlyProfitLoss.put(year + " " + month, totalProfitloss);
        });

        return monthlyProfitLoss;
    }

    @Override
    public BigDecimal getAllProfitLoss() {

        List<InvoiceProductDTO> invoiceProductList = invoiceProductService.listAll();

        return  invoiceProductList.stream().map(invoiceProductDTO -> invoiceProductDTO.getProfitLoss())
                .reduce(BigDecimal::add).get();

    }


    @Override
    public BigDecimal getTotalCost() {

        List<InvoiceProductDTO> invoice = invoiceProductService.findAllByInvoiceStatusAndInvoiceTypeAndCompany(InvoiceStatus.APPROVED, InvoiceType.PURCHASE);

        BigDecimal costTotalForQty = BigDecimal.ZERO;

        BigDecimal totalCost = BigDecimal.ZERO;

        for (InvoiceProductDTO invoiceProductDTO : invoice) {
             costTotalForQty = invoiceProductDTO.getPrice().multiply(
                    BigDecimal.valueOf(invoiceProductDTO.getQuantity() * (invoiceProductDTO.getTax() + 100) / 100d));

             totalCost = totalCost.add(costTotalForQty);
        }

        return totalCost;
    }

    @Override
    public BigDecimal getTotalSales() {

        List<InvoiceProductDTO> invoice = invoiceProductService.findAllByInvoiceStatusAndInvoiceTypeAndCompany(InvoiceStatus.APPROVED, InvoiceType.SALES);

        BigDecimal salesTotalForQty = BigDecimal.ZERO;

        BigDecimal totalSales = BigDecimal.ZERO;

        for (InvoiceProductDTO invoiceProductDTO : invoice) {
            salesTotalForQty = invoiceProductDTO.getPrice().multiply(
                    BigDecimal.valueOf(invoiceProductDTO.getQuantity() * (invoiceProductDTO.getTax() + 100) / 100d));

            totalSales = totalSales.add(salesTotalForQty);
        }

        return totalSales;

    }

}
