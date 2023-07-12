package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.SecurityService;
import com.sc.accounting_smart_cookies.service.StockReportService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StockReportImpl implements StockReportService {
    private final InvoiceProductService invoiceProductService;
    private final SecurityService securityService;

    public StockReportImpl(InvoiceProductService invoiceProductService, SecurityService securityService) {
        this.invoiceProductService = invoiceProductService;
        this.securityService = securityService;
    }

    @Override
    public List<InvoiceProductDTO> generateStockReport() {
        return invoiceProductService.findAllInvoicesByStatusApproved(InvoiceStatus.APPROVED, securityService.getLoggedInUser().getCompany().getTitle());
    }
}
