package com.sc.accounting_smart_cookies.converter;

import com.sc.accounting_smart_cookies.dto.CategoryDTO;
import com.sc.accounting_smart_cookies.service.CategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryDTOConverter implements Converter<String, CategoryDTO> {

    CategoryService categoryService;

    public CategoryDTOConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDTO convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }
        return categoryService.findById(Long.parseLong(source));
    }
}
