package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.converter.InvoiceDTOConverter;
import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.InvoiceRepository;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import com.sc.accounting_smart_cookies.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceDTOConverter invoiceDTOConverter;
    private final SecurityService securityService;

    @Override
    public List<InvoiceDTO> findAll() {

        List<Invoice> invoices = invoiceRepository.findAllByInvoiceType(InvoiceType.PURCHASE);

        return invoices.stream().map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> findInvoicesByType(InvoiceType invoiceType) {

        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());

        List<Invoice> invoices = invoiceRepository.findInvoicesByCompanyAndInvoiceType(company, invoiceType);

        return invoices.stream().map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO findById(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow();

        return mapperUtil.convert(invoice, new InvoiceDTO());
    }

    @Override
    public InvoiceDTO getNewInvoice(InvoiceType invoiceType) {

        InvoiceDTO newInvoice = new InvoiceDTO();
        newInvoice.setDate(LocalDate.now());
        newInvoice.setInvoiceNo(generateInvoiceNo(invoiceType));

        return newInvoice;
    }

    @Override
    public void deleteById(Long id) {

        Optional<Invoice> invoice = invoiceRepository.findById(id);

        if (invoice.isPresent()) {
            invoice.get().setIsDeleted(true);
            invoiceRepository.save(invoice.get());
        }
    }

    @Override
    public InvoiceDTO save(InvoiceDTO invoiceDTO, InvoiceType invoiceType) {

        Invoice invoice = mapperUtil.convert(invoiceDTO, new Invoice());
        invoice.setInvoiceType(invoiceType);
        invoice.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        invoice.setCompany(mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company()));
        Invoice returnInvoice = invoiceRepository.save(invoice);

        return mapperUtil.convert(returnInvoice, new InvoiceDTO());
    }

    private String generateInvoiceNo(InvoiceType invoiceType) {

        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());

        List<Invoice> invoices = invoiceRepository.findInvoicesByCompanyAndInvoiceType(company, invoiceType);
        if (invoices.size() == 0) {
            return invoiceType.name().charAt(0) + "-001";
        }

        Invoice lastCreatedInvoiceOfTheCompany = invoices.stream()
                .max(Comparator.comparing(Invoice::getInsertDateTime)).get();

        int newOrder = Integer.parseInt(lastCreatedInvoiceOfTheCompany.getInvoiceNo().substring(2)) + 1;
        return invoiceType.name().charAt(0) + "-" + String.format("%03d", newOrder);
    }

}
