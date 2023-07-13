package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.entity.ClientVendor;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.InvoiceProduct;
import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.InvoiceRepository;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.InvoiceService;
import com.sc.accounting_smart_cookies.service.SecurityService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final InvoiceProductService invoiceProductService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil,
                              SecurityService securityService, @Lazy InvoiceProductService invoiceProductService) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public List<InvoiceDTO> findAll() {

        List<Invoice> invoices = invoiceRepository.findAllByInvoiceType(InvoiceType.PURCHASE);

        return invoices.stream().map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO()))
                .peek(this::calculateInvoiceDetails)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> findInvoicesByType(InvoiceType invoiceType) {

        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());

        List<Invoice> invoices = invoiceRepository.findInvoicesByCompanyAndInvoiceType(company, invoiceType);

        return invoices.stream().map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO()))
                .peek(this::calculateInvoiceDetails)
                .collect(Collectors.toList());
    }

    private void calculateInvoiceDetails(InvoiceDTO dto) {

        BigDecimal price = getTotalPriceOfInvoice(dto);
        BigDecimal tax = getTotalTaxOfInvoice(dto);

        dto.setPrice(price);
        dto.setTax(tax.intValue());
        dto.setTotal(price.add(tax));
    }

    private BigDecimal getTotalTaxOfInvoice(InvoiceDTO dto) {

        List<InvoiceProductDTO> invoiceProductDTOS = invoiceProductService.findAllByInvoiceId(dto.getId());

        return invoiceProductDTOS.stream().map(p -> p.getPrice().multiply(
                                BigDecimal.valueOf(p.getQuantity() * p.getTax() / 100d))
                        .setScale(2, RoundingMode.HALF_UP))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private BigDecimal getTotalPriceOfInvoice(InvoiceDTO dto) {

        List<InvoiceProductDTO> invoiceProductDTOS = invoiceProductService.findAllByInvoiceId(dto.getId());

        return invoiceProductDTOS.stream().map(p -> p.getPrice().multiply(BigDecimal.valueOf((long) p.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public InvoiceDTO findById(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow();

        InvoiceDTO dto = mapperUtil.convert(invoice, new InvoiceDTO());
        calculateInvoiceDetails(dto);

        return dto;
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
         List<InvoiceProductDTO>invoiceProductDTOS=invoiceProductService.findAllByInvoiceId(id);
        for (InvoiceProductDTO dto : invoiceProductDTOS){
            invoiceProductService.deleteById(dto.getId());
        }

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

    @Override
    public void approveInvoiceById(Long id) {

        Optional<Invoice> invoice = invoiceRepository.findById(id);

        if (invoice.isPresent()) {
            invoiceProductService.completeApproval(id, invoice.get().getInvoiceType());
            invoice.get().setInvoiceStatus(InvoiceStatus.APPROVED);
            invoice.get().setDate(LocalDate.now());
            invoiceRepository.save(invoice.get());
            mapperUtil.convert(invoice, new InvoiceDTO());
        }

    }

    @Override
    public InvoiceDTO update(Long id, InvoiceDTO invoiceDTO) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
        invoice.setClientVendor(mapperUtil.convert(invoiceDTO.getClientVendor(), new ClientVendor()));
        invoiceRepository.save(invoice);

        return mapperUtil.convert(invoice, new InvoiceDTO());
    }

    @Override
    public List<InvoiceDTO> findTop3ByCompanyOrderByDateDesc() {

        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());


        return invoiceRepository.findTop3ByCompanyOrderByDateDesc(company).stream()
                .map(invoice -> {
                    InvoiceDTO invoiceDTO = mapperUtil.convert(invoice, new InvoiceDTO());
                    invoiceDTO.setPrice(getTotalPriceOfInvoice(invoiceDTO));
                    invoiceDTO.setTax(getTotalTaxOfInvoice(invoiceDTO).intValue());
                    invoiceDTO.setTotal(getTotalPriceOfInvoice(invoiceDTO).add(getTotalTaxOfInvoice(invoiceDTO)));

                    return invoiceDTO;
                }

                )
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO printInvoice(Long id) {
        InvoiceDTO invoiceDto = mapperUtil.convert(invoiceRepository.findById(id).get(), new InvoiceDTO());
        calculateInvoiceDetails(invoiceDto);
        return invoiceDto;
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


    @Override
    public boolean existsByClientVendorId(Long id) {

        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return invoiceRepository.existsByCompanyAndClientVendorId(company, id);
    }


}
