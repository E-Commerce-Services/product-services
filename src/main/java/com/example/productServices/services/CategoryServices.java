package com.example.productServices.services;

import com.example.productServices.dtos.server.CategoryManipulationMappedDTO;
import com.example.productServices.exceptions.CategoryDeletionNotAllowed;
import com.example.productServices.exceptions.CategoryNotFoundException;
import com.example.productServices.exceptions.DuplicateCategoryException;
import com.example.productServices.models.Category;
import com.example.productServices.repository.CategoryRepository;
import com.example.productServices.commons.DBCommons;
import com.example.productServices.commons.ValidationCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServices {
    private final CategoryRepository categoryRepository;
    private final DBCommons dbCommons;
    private final ValidationCommons validationCommons;

    @Autowired
    public CategoryServices(CategoryRepository categoryRepository, DBCommons dbCommons, ValidationCommons validationCommons){
        this.categoryRepository=categoryRepository;
        this.dbCommons = dbCommons;
        this.validationCommons = validationCommons;
    }

    private Category buildAndValidateCategory(CategoryManipulationMappedDTO categoryManipulationMappedDTO) throws DuplicateCategoryException {

        Category category=Category.builder()
                .name(categoryManipulationMappedDTO.name())
                .build();

        validationCommons.validateEntity(category);

        return category;
    }

    public Category createCategory(CategoryManipulationMappedDTO categoryManipulationMappedDTO) throws DuplicateCategoryException {

        Category newCategory=buildAndValidateCategory(categoryManipulationMappedDTO);

        return categoryRepository.save(newCategory);
    }

    public Category updateCategory(Long categoryId,CategoryManipulationMappedDTO categoryManipulationMappedDTO) throws CategoryNotFoundException, DuplicateCategoryException {

        validationCommons.validateCategoryExistence(categoryId);

        Category updatedCategory=buildAndValidateCategory(categoryManipulationMappedDTO);
        updatedCategory.setId(categoryId);

        return categoryRepository.save(updatedCategory);
    }

    public Category deleteCategory(Long categoryId) throws CategoryNotFoundException, CategoryDeletionNotAllowed {

        Category category= dbCommons.getCategoryOrNull(categoryId);
        validationCommons.validateCategoryDeletable(categoryId);

        categoryRepository.deleteById(categoryId);

        return category;
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
}
