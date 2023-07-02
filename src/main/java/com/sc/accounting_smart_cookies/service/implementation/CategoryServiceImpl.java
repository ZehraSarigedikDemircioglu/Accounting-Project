package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.*;
import com.sc.accounting_smart_cookies.entity.*;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.CategoryRepository;
import com.sc.accounting_smart_cookies.repository.CompanyRepository;
import com.sc.accounting_smart_cookies.repository.UserRepository;
import com.sc.accounting_smart_cookies.service.CategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository, CompanyRepository companyRepository, MapperUtil mapperUtil) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return mapperUtil.convert(category, new CategoryDTO());
    }


    @Override
    public List<CategoryDTO> listAllCategories() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User LoggedInUser = userRepository.findByUsername(auth.getName());

            Company company = companyRepository.findById(LoggedInUser.getCompany().getId()).orElseThrow();

            List<Category> categoryList = categoryRepository.findAllByCompany(company);
            return categoryList.stream().map(category -> mapperUtil.convert(category, new CategoryDTO())).
                    collect(Collectors.toList());

    }


    @Override
    public void deleteById(Long id) {
        Optional<Category> foundCategory = categoryRepository.findById(id);
        if (foundCategory.isPresent()) {
            foundCategory.get().setIsDeleted(true);
            categoryRepository.save(foundCategory.get());
        }
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = mapperUtil.convert(categoryDTO, new Category());
        categoryRepository.save(category);
        return mapperUtil.convert(category, new CategoryDTO());
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        Category convertedCategory = mapperUtil.convert(categoryDTO, new Category());
        convertedCategory.setId(category.getId());
        categoryRepository.save(convertedCategory);
        return mapperUtil.convert(convertedCategory, new CategoryDTO());
    }

//    @Override
//    public List<CategoryDTO> listAllCategoriesByCompany(Company company) {
//
//        Category category = categoryRepository.findAllByCompany()
//
//        return null;
//    }
}
