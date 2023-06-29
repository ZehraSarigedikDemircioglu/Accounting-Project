package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.entity.Product;
import com.sc.accounting_smart_cookies.mapper.ProductMapper;
import com.sc.accounting_smart_cookies.repository.ProductRepository;
import com.sc.accounting_smart_cookies.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return productMapper.convertToDto(product);
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
        return productList.stream().map(productMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProductDTO productDTO) {
        Product product = productMapper.convertToEntity(productDTO);
        productRepository.save(product);
    }

    @Override
    public void update(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId()).orElseThrow();
        Product convertedProduct = productMapper.convertToEntity(productDTO);
        convertedProduct.setId(product.getId());
        productRepository.save(convertedProduct);
    }
}
