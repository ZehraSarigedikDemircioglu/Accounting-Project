package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.InvoiceProductDTO;
import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.entity.Category;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Product;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.CategoryRepository;
import com.sc.accounting_smart_cookies.repository.ProductRepository;
import com.sc.accounting_smart_cookies.service.CompanyService;
import com.sc.accounting_smart_cookies.service.InvoiceProductService;
import com.sc.accounting_smart_cookies.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;
    private final InvoiceProductService invoiceProductService;

    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        return mapperUtil.convert(product, new ProductDTO());
    }

    @Override
    public void deleteById(Long id) {
//        Optional<Product> foundProduct = productRepository.findById(id);
//        if (foundProduct.isPresent()) {
//            foundProduct.get().setIsDeleted(true);
//            productRepository.save(foundProduct.get());
//        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(product -> mapperUtil.convert(product, new ProductDTO())).collect(Collectors.toList());
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = mapperUtil.convert(productDTO, new Product());
        product.setCompany(mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company()));
        productRepository.save(product);
        return mapperUtil.convert(product, new ProductDTO());
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        Product convertedProduct = mapperUtil.convert(productDTO, new Product());
        convertedProduct.setId(product.getId());
        convertedProduct.setQuantityInStock(product.getQuantityInStock());
        convertedProduct.setCompany(mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company()));
        productRepository.save(convertedProduct);
        return mapperUtil.convert(convertedProduct, new ProductDTO());
    }

    @Override
    public ProductDTO updateQuantity(ProductDTO productDTO) {

        Product product = mapperUtil.convert(productDTO, new Product());
        product.setId(product.getId());
        product.setQuantityInStock(product.getQuantityInStock());
        product.setCompany(mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company()));
        productRepository.save(product);
        return mapperUtil.convert(product, new ProductDTO());
    }

    @Override
    public List<ProductDTO> findAllByCompany() {
        return productRepository.findAllByCompany(mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company())).stream()
                .sorted(Comparator.comparing((Product product) -> product.getCategory().getDescription())
                        .thenComparing(Product::getName))
                .map(product -> mapperUtil.convert(product, new ProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isProductNameExist(ProductDTO productDTO) {

        Product existedProduct = productRepository.findByNameAndCategoryCompany(productDTO.getName(), mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company()));
        if (existedProduct == null) {
            return false;
        }
        return !existedProduct.getId().equals(productDTO.getId());
    }

    @Override
    public boolean checkProductHasInvoice(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        List<InvoiceProductDTO> invoiceProductDTOs = invoiceProductService.findAllInvoiceProductsByProductId(id);
        return invoiceProductDTOs.size() > 0 || product.getQuantityInStock() > 0;
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow();

        List<Product> products = productRepository.findByCategory(category);

        return products.stream().map(product -> mapperUtil.convert(product, new ProductDTO())).collect(Collectors.toList());
    }

}
