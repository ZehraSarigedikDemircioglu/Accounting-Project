package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Product;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.ProductRepository;
import com.sc.accounting_smart_cookies.service.CompanyService;
import com.sc.accounting_smart_cookies.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;

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
    public List<ProductDTO> findAllByCompany() {
        return productRepository.findAllByCompany(mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company())).stream()
                .sorted(Comparator.comparing((Product product) -> product.getCategory().getDescription())
                        .thenComparing(Product::getName))
                .map(product -> mapperUtil.convert(product, new ProductDTO()))
                .collect(Collectors.toList());
    }
}
