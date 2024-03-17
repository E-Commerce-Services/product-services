package com.example.productServices.commons.repositorycommons;

import com.example.productServices.commons.validator.CategoryEntityValidator;
import com.example.productServices.models.Category;
import com.example.productServices.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryRepositoryCommons extends RepositoryCommons<Category> {


    public CategoryRepositoryCommons(CategoryEntityValidator categoryEntityValidator, CategoryRepository categoryRepository) {
        super(categoryEntityValidator, categoryRepository);
    }
}
