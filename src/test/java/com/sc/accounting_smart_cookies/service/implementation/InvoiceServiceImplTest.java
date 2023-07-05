package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.TestDocumentInitializer;
import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.dto.InvoiceDTO;
import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Invoice;
import com.sc.accounting_smart_cookies.entity.User;
import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceStatus;
import com.sc.accounting_smart_cookies.enums.InvoiceType;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.InvoiceRepository;
import com.sc.accounting_smart_cookies.service.SecurityService;
import org.bouncycastle.voms.VOMSAttribute;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @InjectMocks
    InvoiceServiceImpl service;

    @Mock
    InvoiceRepository invoiceRepository;
    @Mock
    SecurityService securityService;

    @Spy
    static MapperUtil mapperUtil = new MapperUtil(new ModelMapper());

    private List<Invoice> getInvoices() {

        Invoice invoice = TestDocumentInitializer.getInvoiceEntity(
                any(InvoiceStatus.class), any(InvoiceType.class));
        Invoice invoice2 = TestDocumentInitializer.getInvoiceEntity(
                any(InvoiceStatus.class), any(InvoiceType.class));

        return List.of(invoice, invoice2);
    }

    private List<InvoiceDTO> getInvoiceDTOs() {

        InvoiceDTO invoiceDTO = TestDocumentInitializer.getInvoice(
                any(InvoiceStatus.class), any(InvoiceType.class));
        InvoiceDTO invoiceDTO2 = TestDocumentInitializer.getInvoice(
                any(InvoiceStatus.class), any(InvoiceType.class));

        return List.of(invoiceDTO, invoiceDTO2);
    }

    @Test
    void PASS_find_all_invoices1() {

        // when
        when(invoiceRepository.findAllByInvoiceType(any())).thenReturn(getInvoices());

        List<InvoiceDTO> expectedList = getInvoiceDTOs();

        List<InvoiceDTO> actualList = service.findAll();

        //then
        assertThat(actualList).usingRecursiveComparison().isEqualTo(expectedList);
    }

    @Test
    void PASS_find_all_invoices() {

        // when
        List<Invoice> invoiceList = invoiceRepository.findAllByInvoiceType(any());
        List<InvoiceDTO> invoiceDTOList = invoiceList.stream().map(invoice ->
                        mapperUtil.convert(invoiceList, new InvoiceDTO()))
                .collect(Collectors.toList());

        List<InvoiceDTO> returnInvoices = service.findAll();

        //then
        assertThat(returnInvoices).usingRecursiveComparison().isEqualTo(invoiceDTOList);
                            //      ^^ compares VALUES instead of OBJECTS
    }

//    @Test
//    void findInvoicesByType() {
//    }

    @Test
    void PASS_find_invoices_by_existing_invoice_type() {
        // given
        Company company = TestDocumentInitializer.getCompanyEntity(CompanyStatus.ACTIVE);

        Invoice invoice = TestDocumentInitializer.getInvoiceEntity(InvoiceStatus.AWAITING_APPROVAL, InvoiceType.PURCHASE);
        invoice.setCompany(company);

        UserDTO userDTO = TestDocumentInitializer.getUser("Admin");
        userDTO.setCompany(mapperUtil.convert(company, new CompanyDTO()));

        // when
//        doReturn(userDTO).when(securityService).getLoggedInUser();
        when(securityService.getLoggedInUser()).thenReturn(userDTO);

        List<Invoice> invoices = invoiceRepository.findInvoicesByCompanyAndInvoiceType(
                invoice.getCompany(), invoice.getInvoiceType());

        List<Invoice> returnedInvoices = service.findInvoicesByType(invoice.getInvoiceType())
                .stream().map(returnInvoice -> mapperUtil.convert(returnInvoice, new Invoice()))
                .collect(Collectors.toList());

        //then
        assertThat(returnedInvoices.equals(invoices));
    }
// ***COMPLETE:***
    @Test
    void FAIL_find_invoices_by_NULL_invoice_type() {
        // given
        Company company = TestDocumentInitializer.getCompanyEntity(CompanyStatus.ACTIVE);

        Invoice invoice = TestDocumentInitializer.getInvoiceEntity(InvoiceStatus.AWAITING_APPROVAL, null);
        invoice.setCompany(company);

        UserDTO userDTO = TestDocumentInitializer.getUser("Admin");
        userDTO.setCompany(mapperUtil.convert(company, new CompanyDTO()));

        // when
        doReturn(userDTO).when(securityService).getLoggedInUser();

        List<Invoice> invoices = invoiceRepository.findInvoicesByCompanyAndInvoiceType(
                invoice.getCompany(), invoice.getInvoiceType());

        List<Invoice> returnedInvoices = service.findInvoicesByType(invoice.getInvoiceType())
                .stream().map(returnInvoice -> mapperUtil.convert(returnInvoice, new Invoice()))
                .collect(Collectors.toList());

        //then
//        assertThat(returnedInvoices.equals(invoices));         // ***COMPLETE:***
    }

//    @Test
//    void findById() {
//    }

    @Test
    void PASS_find_by_existing_id() {
        // given
        Invoice invoice = TestDocumentInitializer.getInvoiceEntity(InvoiceStatus.AWAITING_APPROVAL, InvoiceType.PURCHASE);

        // when
        when(invoiceRepository.findById(invoice.getId())).thenReturn(Optional.of(invoice));
        var returnedInvoice = service.findById(invoice.getId());
        //then
        assertThat(returnedInvoice.getInvoiceNo().equals(invoice.getInvoiceNo()));
    }

    @Test
    void FAIL_find_by_NON_existing_id() {
        // when
        when(invoiceRepository.findById(anyLong())).thenThrow(NoSuchElementException.class);
        //then
        assertThrows(NoSuchElementException.class, () -> service.findById(anyLong()));
    }

    @Test
    void FAIL_find_by_NULL_id() {
        // when
        when(invoiceRepository.findById(null)).thenThrow(NoSuchElementException.class);
        //then
        assertThrows(NoSuchElementException.class, () -> service.findById(null));
    }

//    @Test
//    void getNewInvoice() {
//    }

    @Test
    void PASS_get_new_invoice() {

        InvoiceDTO invoiceDTO = mapperUtil.convert(TestDocumentInitializer.getInvoice(
                any(), any()), new InvoiceDTO());

        UserDTO userDTO = TestDocumentInitializer.getUser("Admin");
        userDTO.setCompany(mapperUtil.convert(invoiceDTO.getCompany(), new CompanyDTO()));

        // when
//        doReturn(userDTO).when(securityService).getLoggedInUser();
        when(securityService.getLoggedInUser()).thenReturn(userDTO);

        assertThat(service.getNewInvoice(InvoiceType.PURCHASE).equals(invoiceDTO));
    }


    @Test
    void deleteById() {
    }

    @Test
    void save() {
    }

    @Test
    void approveInvoiceById() {
    }

    @Test
    void update() {
    }
}