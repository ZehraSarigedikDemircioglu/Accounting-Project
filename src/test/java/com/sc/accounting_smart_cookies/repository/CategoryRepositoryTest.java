package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.TestDocumentInitializer;
import com.sc.accounting_smart_cookies.entity.Category;
import com.sc.accounting_smart_cookies.enums.CompanyStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void findAllByCompany() {

        // given
        int sizeBeforeSlush = repository.findAllByCompanyAndIsDeleted(TestDocumentInitializer.getCompanyEntity(CompanyStatus.ACTIVE), false).size();

        Category category = TestDocumentInitializer.getCategoryEntity();
        entityManager.persist(category);
        entityManager.flush();

        // when
        List<Category> categories = repository.findAllByCompanyAndIsDeleted(TestDocumentInitializer.getCompanyEntity(CompanyStatus.ACTIVE), false);

        // then
        assertThat(sizeBeforeSlush == categories.size() - 1);
    }

    @Test
    void findByDescriptionAndCompany() {
    }
}