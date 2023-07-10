package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.*;
import com.sc.accounting_smart_cookies.entity.*;
import com.sc.accounting_smart_cookies.exceptions.CategoryNotFoundException;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.service.ProductService;
import com.sc.accounting_smart_cookies.repository.CategoryRepository;
import com.sc.accounting_smart_cookies.repository.ProductRepository;
import com.sc.accounting_smart_cookies.service.CategoryService;
import com.sc.accounting_smart_cookies.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CompanyService companyService;
    private final MapperUtil mapperUtil;

    private final ProductService productService;


    public CategoryServiceImpl(CategoryRepository categoryRepository, CompanyService companyService, MapperUtil mapperUtil, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.companyService = companyService;
        this.mapperUtil = mapperUtil;
        this.productService = productService;
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("This Category can not be found in the system."));
        return mapperUtil.convert(category, new CategoryDTO());
    }

    @Override
    public boolean findByDescription(String description) {
        Category category = categoryRepository.findByDescription(description);
        return description != null;
    }


    @Override
    public List<CategoryDTO> listAllCategories() {
        Company company = mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company());
        List<Category> categoryList = categoryRepository.findAllByCompanyAndIsDeleted(company, false);
        return categoryList.stream().map(category -> mapperUtil.convert(category, new CategoryDTO())).
                collect(Collectors.toList());

    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findByIdAndIsDeleted(id, false);
        category.setIsDeleted(true);
        categoryRepository.save(category);

    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = mapperUtil.convert(categoryDTO, new Category());
        category.setCompany(mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company()));
        categoryRepository.save(category);
        return mapperUtil.convert(category, new CategoryDTO());
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        Category convertedCategory = mapperUtil.convert(categoryDTO, new Category());
        convertedCategory.setId(category.getId());
        convertedCategory.setCompany(mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company()));
        categoryRepository.save(convertedCategory);
        return mapperUtil.convert(convertedCategory, new CategoryDTO());
    }

    @Override
    public boolean isCategoryDescriptionUnique(CategoryDTO categoryDTO) {
        Company company = mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company());
        Category existingCategory = categoryRepository.findByDescriptionAndCompany(categoryDTO.getDescription(), company);
        if (existingCategory == null) return false;
        return !existingCategory.getId().equals(categoryDTO.getId());
    }

    @Override
    public boolean hasProducts(CategoryDTO categoryDTO) {
        List<ProductDTO> products = productService.getProductsByCategory(categoryDTO.getId());
        return !products.isEmpty();

    }





}
