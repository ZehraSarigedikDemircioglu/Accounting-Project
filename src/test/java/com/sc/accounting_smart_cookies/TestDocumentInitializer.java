package com.sc.accounting_smart_cookies;

import com.sc.accounting_smart_cookies.dto.*;
import com.sc.accounting_smart_cookies.entity.*;
import com.sc.accounting_smart_cookies.enums.*;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class TestDocumentInitializer {

    @Autowired
    static MapperUtil mapperUtil = new MapperUtil(new ModelMapper());

    public static UserDTO getUser(String role){
        return UserDTO.builder()
                .id(1L)
                .firstname("John")
                .lastname("Mike")
                .phone("+1 (111) 111-1111")
                .password("Abc1")
                .confirmPassword("Abc1")
                .role(new RoleDTO(1L,role))
                .isOnlyAdmin(false)
                .company(getCompany(CompanyStatus.ACTIVE))
                .build();
    }

    public static User getUserEntity(String role){
        return mapperUtil.convert(getUser(role), new User());
    }

    public static CompanyDTO getCompany(CompanyStatus status){
        return CompanyDTO.builder()
                .title("Test_Company")
                .website("www.test.com")
                .id(1L)
                .phone("+1 (111) 111-1111")
                .companyStatus(status)
                .address(new AddressDTO())
                .build();
    }

    public static Company getCompanyEntity(CompanyStatus status){
        return mapperUtil.convert(getCompany(status), new Company());

    }

    public static CategoryDTO getCategory(){
        return CategoryDTO.builder()
                .company(getCompany(CompanyStatus.ACTIVE))
                .description("Test_Category")
                .build();
    }

    public static Category getCategoryEntity(){
        return mapperUtil.convert(getCategory(), new Category());
    }

    public static ClientVendorDTO getClientVendor(ClientVendorType type){
        return ClientVendorDTO.builder()
                .clientVendorType(type)
                .clientVendorName("Test_ClientVendor")
                .address(new AddressDTO())
                .website("https://www.test.com")
                .phone("+1 (111) 111-1111")
                .build();
    }

    public static ClientVendor getClientVendorEntity(ClientVendorType type){
        return mapperUtil.convert(getClientVendor(type), new ClientVendor());
    }

    public static ProductDTO getProduct(){
        return ProductDTO.builder()
                .category(getCategory())
                .productUnit(ProductUnit.PCS)
                .name("Test_Product")
                .quantityInStock(10)
                .lowLimitAlert(5)
                .build();
    }
    public static Product getProductEntity(){
        return mapperUtil.convert(getProduct(), new Product());
    }

    public static InvoiceProductDTO getInvoiceProduct(){
        return InvoiceProductDTO.builder()
                .product(getProduct())
                .price(BigDecimal.TEN)
                .tax(10)
                .quantity(10)
                .invoice(new InvoiceDTO())
                .build();
    }

    public static Invoice getInvoiceProductEntity(){
        return mapperUtil.convert(getInvoiceProduct(), new Invoice());
    }

    public static InvoiceDTO getInvoice(InvoiceStatus status, InvoiceType type){
        return InvoiceDTO.builder()
                .invoiceNo("T-001")
                .clientVendor(getClientVendor(ClientVendorType.CLIENT))
                .invoiceStatus(status)
                .invoiceType(type)
                .date(LocalDate.of(2022,01,01))
                .company(getCompany(CompanyStatus.ACTIVE))
                .invoiceProducts(new ArrayList<>(Arrays.asList(getInvoiceProduct())))
                .price(BigDecimal.valueOf(1000))
                .tax(10)
                .total(BigDecimal.TEN.multiply(BigDecimal.valueOf(1000)))
                .build();
    }

    public static Invoice getInvoiceEntity(InvoiceStatus status, InvoiceType type){
        return mapperUtil.convert(getInvoice(status, type), new Invoice());
    }
}
