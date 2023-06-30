package com.sc.accounting_smart_cookies.converter;

import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.service.ProductService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ProductDTOConverter implements Converter<String, ProductDTO> {
    ProductService productService;

    public ProductDTOConverter(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductDTO convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }
        return productService.findById(Long.parseLong(source));
    }
}
