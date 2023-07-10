package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.TestDocumentInitializer;
import com.sc.accounting_smart_cookies.dto.CategoryDTO;
import com.sc.accounting_smart_cookies.dto.CompanyDTO;
import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.entity.*;
import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import com.sc.accounting_smart_cookies.enums.ProductUnit;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.CompanyRepository;
import com.sc.accounting_smart_cookies.repository.ProductRepository;
import com.sc.accounting_smart_cookies.service.CompanyService;
import com.sc.accounting_smart_cookies.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CountedCompleter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CompanyService companyService;
    @InjectMocks
    ProductServiceImpl productService;

    @Spy
    private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());

    Product product;
    ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("electronic");
        product.setId(1L);
        product.setQuantityInStock(50);
        product.setLowLimitAlert(2);
        product.setCompany(new Company());
        product.setCategory(new Category());

        productDTO = new ProductDTO();
        productDTO.setName("electronic");
        productDTO.setId(1L);
        productDTO.setQuantityInStock(50);
        productDTO.setLowLimitAlert(2);
    }

    private List<Product> getProducts() {
        Product product2 = new Product();
        product2.setId(2L);
        product2.setQuantityInStock(10);
        product2.setLowLimitAlert(3);
        product2.setCompany(new Company());
        product2.setCategory(new Category());
        return List.of(product, product2);
    }

    private List<ProductDTO> getProductDTOs() {
        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setId(2L);
        productDTO2.setQuantityInStock(10);
        productDTO2.setLowLimitAlert(3);
        return List.of(productDTO, productDTO2);
    }

    @Test
    @DisplayName("When_find_by_id_then_success")
    public void GIVEN_ID_WHEN_FIND_BY_ID_THEN_SUCCESS() {
        // Given
        Product product = TestDocumentInitializer.getProductEntity();
        // When
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        var returnedUser = productService.findById(product.getId());
        // Then
        assertThat(returnedUser.getName().equals(product.getName()));
    }

    @Test
    @DisplayName("When_given_non_existing_id_then_fail")
    public void GIVEN_NON_EXISTING_ID_WHEN_FIND_BY_ID_THEN_FAIL() {
        when(productRepository.findById(anyLong())).thenThrow(NoSuchElementException.class); // Mockito return null by itself...
        assertThrows(NoSuchElementException.class, () -> productService.findById(anyLong()));
    }

    @Test
    @DisplayName("When_given_null_id_then_fail")
    public void GIVEN_NULL_ID_WHEN_FIND_BY_ID_THEN_FAIL() {
        when(productRepository.findById(null)).thenThrow(NoSuchElementException.class); // Mockito return null by itself...
        assertThrows(NoSuchElementException.class, () -> productService.findById(null));
    }

    @Test
    void should_list_all_products() {
        //stub
        when(productRepository.findAll()).thenReturn(getProducts());
//        List<ProductDTO> expectedList = getProductDTOs();
        List<ProductDTO> actualList = productService.findAll();
        assertThat(actualList).usingRecursiveComparison().isEqualTo(getProductDTOs()); // check values foe each fields, not object
    }

    @Test
    void should_find_product_by_id() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        ProductDTO actualProductDTO = productService.findById(anyLong());
        assertThat(actualProductDTO).usingRecursiveComparison().ignoringActualNullFields().isEqualTo(productDTO);
    }

    @Test
    void should_throw_exception_when_find_product_id_doesnt_exist() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        Throwable throwable = catchThrowable(() ->
                productService.findById(anyLong()));
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Product not found");
    }

    @Test
    void should_save_product() {
        CompanyDTO company = TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);
//        doReturn(company).when(companyService).getCompanyOfLoggedInUser();
        when(companyService.getCompanyOfLoggedInUser()).thenReturn(company);
        when(productRepository.save(any(Product.class))).thenReturn(TestDocumentInitializer.getProductEntity());
        ProductDTO savedActual = productService.save(productDTO);
        assertThat(savedActual).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(productDTO);
    }

    @Test
    void should_find_all_products_by_company() {
//        doReturn(product).when(productRepository).findAllByCompany(any(Company.class));
        List<Product> productList = new ArrayList<>();
        productList.add(TestDocumentInitializer.getProductEntity());
        when(productRepository.findAllByCompany(any(Company.class))).thenReturn(productList);
        CompanyDTO company = TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);
        when(companyService.getCompanyOfLoggedInUser()).thenReturn(company);
        List<ProductDTO> savedActual = productService.findAllByCompany();
        List<ProductDTO> expectedProductList = new ArrayList<>();
        expectedProductList.add(TestDocumentInitializer.getProduct());
        assertThat(savedActual).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedProductList);
    }
}