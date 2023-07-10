package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.entity.Payment;
import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.enums.Months;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.InvoiceProductRepository;
import com.sc.accounting_smart_cookies.repository.InvoiceRepository;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.ReportingService;
import com.sc.accounting_smart_cookies.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportingServiceImpl implements ReportingService {
    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final InvoiceProductService invoiceProductService;
    private final InvoiceProductRepository invoiceProductRepository;
//
//    @Override
//    public BigDecimal setProfitLossOfInvoiceProductsForSalesInvoice(InvoiceProduct toBeSoldProduct) {
//
//        List<InvoiceProduct> purchasedProducts = invoiceProductRepository
//                .findInvoiceProductsByInvoiceInvoiceTypeAndProductAndRemainingQuantityNotOrderByIdAsc(
//               InvoiceType.PURCHASE,toBeSoldProduct.getProduct(),0);
//
//        BigDecimal profitLoss = BigDecimal.ZERO;
//
//
//        for (InvoiceProduct purchasedProduct : purchasedProducts) {
//            if (toBeSoldProduct.getRemainingQuantity()==null){
//                break;
//            }
//            if (toBeSoldProduct.getRemainingQuantity() <= purchasedProduct.getRemainingQuantity()) {
//                BigDecimal costTotalForQty = purchasedProduct.getPrice().
//                        multiply(BigDecimal.valueOf(purchasedProduct.getQuantity()))
//                        .multiply(BigDecimal.valueOf((purchasedProduct.getTax() + 100) / 100d));
//                BigDecimal salesTotalForQty = toBeSoldProduct.getPrice().
//                        multiply(BigDecimal.valueOf(toBeSoldProduct.getQuantity()))
//                        .multiply(BigDecimal.valueOf((toBeSoldProduct.getTax() + 100) / 100d));
//                profitLoss = costTotalForQty.subtract(salesTotalForQty);
//
//                purchasedProduct.setRemainingQuantity(purchasedProduct.getRemainingQuantity() - toBeSoldProduct.getRemainingQuantity());
//                toBeSoldProduct.setProfitLoss(profitLoss);
//                toBeSoldProduct.setRemainingQuantity(0);
//                invoiceProductService.save(mapperUtil.convert(purchasedProduct, new InvoiceProductDTO()),
//                        purchasedProduct.getInvoice().getId());
//                invoiceProductService.save(mapperUtil.convert(toBeSoldProduct, new InvoiceProductDTO()),
//                        toBeSoldProduct.getInvoice().getId());
//
//                break;
//            } else {
//                BigDecimal costTotalForQty = purchasedProduct.getPrice().
//                        multiply(BigDecimal.valueOf(purchasedProduct.getQuantity()))
//                        .multiply(BigDecimal.valueOf((purchasedProduct.getTax() + 100) / 100d));
//                BigDecimal salesTotalForQty = toBeSoldProduct.getPrice().
//                        multiply(BigDecimal.valueOf(purchasedProduct.getQuantity()))
//                        .multiply(BigDecimal.valueOf((toBeSoldProduct.getTax() + 100) / 100d));
//                profitLoss = costTotalForQty.subtract(salesTotalForQty);
//                purchasedProduct.setRemainingQuantity(0);
//                toBeSoldProduct.setRemainingQuantity(toBeSoldProduct.getRemainingQuantity());
//                purchasedProduct.setProfitLoss(profitLoss);
//                toBeSoldProduct.setProfitLoss(profitLoss);
//                invoiceProductService.save(mapperUtil.convert(purchasedProduct, new InvoiceProductDTO()),
//                        purchasedProduct.getInvoice().getId());
//                invoiceProductService.save(mapperUtil.convert(toBeSoldProduct, new InvoiceProductDTO()),
//                        toBeSoldProduct.getInvoice().getId());
//
//            }
//        }
//        return profitLoss;
//    }

    @Override
    public Map<String, BigDecimal> listMonthlyProfitLoss() {
        List<InvoiceProduct> salesList = invoiceProductRepository.findAllByInvoiceInvoiceStatusAndInvoiceInvoiceTypeAndInvoiceCompanyTitle(
                InvoiceStatus.APPROVED, InvoiceType.SALES, securityService.getLoggedInUser().getCompany().getTitle());

        Map<String, BigDecimal> monthlyProfitLoss = new HashMap<>();

        salesList.forEach(invoiceProduct -> {
            String month = invoiceProduct.getInvoice().getDate().getMonth().toString();
            BigDecimal profitLoss = invoiceProduct.getProfitLoss();

            monthlyProfitLoss.put(month, monthlyProfitLoss.getOrDefault(month, BigDecimal.ZERO).add(profitLoss));
        });

        return monthlyProfitLoss;
    }
//@Override
//public Map<String, BigDecimal> listMonthlyProfitLoss() {
//    List<InvoiceProduct> salesList = invoiceProductRepository.findAll();
//    Map<String, BigDecimal> monthlyProfitLoss = new HashMap<>();
//
//    for (InvoiceProduct invoiceProduct : salesList) {
//        String month=invoiceProduct.getInvoice().getDate().getMonth().toString();
//
//        BigDecimal monthlyProfitLoss1=(invoiceProductService.setProfitLossOfInvoiceProductsForSalesInvoice(invoiceProduct));
//        monthlyProfitLoss.put(month,monthlyProfitLoss1);
//
//    }
//
//    return monthlyProfitLoss;
//}


}