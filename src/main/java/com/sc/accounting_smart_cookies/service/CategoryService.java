package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.CategoryDTO;
import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.entity.Company;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> listAllCategories();

    CategoryDTO findById(Long id);
    boolean findByDescription(String description);

    void deleteById(Long id);

    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO update(CategoryDTO categoryDTO, Long id);

    boolean isCategoryDescriptionUnique(CategoryDTO categoryDTO);

    boolean hasProducts(CategoryDTO categoryDTO);
}
