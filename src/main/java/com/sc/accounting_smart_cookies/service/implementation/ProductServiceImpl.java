package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.entity.Product;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.ProductRepository;
import com.sc.accounting_smart_cookies.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return mapperUtil.convert(product, new ProductDTO());
    }

    @Override
    public void deleteById(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isPresent()) {
            foundProduct.get().setIsDeleted(true);
            productRepository.save(foundProduct.get());
        }
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(product -> mapperUtil.convert(product, new ProductDTO())).collect(Collectors.toList());
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = mapperUtil.convert(productDTO, new Product());
        productRepository.save(product);
        return mapperUtil.convert(product, new ProductDTO());
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        Product convertedProduct = mapperUtil.convert(productDTO, new Product());
        convertedProduct.setId(product.getId());
        productRepository.save(convertedProduct);
        return mapperUtil.convert(convertedProduct,new ProductDTO());
    }
}
