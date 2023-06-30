package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.CategoryDTO;
import com.sc.accounting_smart_cookies.dto.ProductDTO;
import com.sc.accounting_smart_cookies.dto.UserDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> listAllCategories();

    CategoryDTO findById(Long id);

    void deleteById(Long id);

    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO update(CategoryDTO categoryDTO, Long id);
}
