package com.example.productServices.services;

import com.example.productServices.commons.repositorycommons.CategoryRepositoryCommons;
import com.example.productServices.commons.validator.CategoryEntityValidator;
import com.example.productServices.dtos.server.CategoryManipulationMappedDTO;
import com.example.productServices.exceptions.*;
import com.example.productServices.models.Category;
import com.example.productServices.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServices {
    private final CategoryRepository categoryRepository;
    private final CategoryRepositoryCommons categoryRepositoryCommons;
    private final CategoryEntityValidator categoryEntityValidator;

    @Autowired
    public CategoryServices(CategoryRepository categoryRepository,
                            CategoryRepositoryCommons categoryRepositoryCommons,
                            CategoryEntityValidator categoryEntityValidator){
        this.categoryRepository=categoryRepository;
        this.categoryRepositoryCommons = categoryRepositoryCommons;
        this.categoryEntityValidator = categoryEntityValidator;
    }

    private Category buildCategory(CategoryManipulationMappedDTO categoryManipulationMappedDTO)  {

        return Category.builder()
                .name(categoryManipulationMappedDTO.name())
                .build();
    }

    public Category createCategory(CategoryManipulationMappedDTO categoryManipulationMappedDTO) throws DuplicateEntityException {

        Category newCategory= buildCategory(categoryManipulationMappedDTO);
        return categoryRepositoryCommons.validateAndSaveEntity(newCategory);
    }

    public Category updateCategory(Long categoryId,CategoryManipulationMappedDTO categoryManipulationMappedDTO) throws DuplicateEntityException, EntityNotFoundException {

        categoryEntityValidator.validateEntityExistenceAndReturn(categoryId);
        Category updatedCategory= buildCategory(categoryManipulationMappedDTO);
        updatedCategory.setId(categoryId);

        return categoryRepositoryCommons.validateAndSaveEntity(updatedCategory);
    }

    public Category deleteCategory(Long categoryId) throws EntityNotFoundException, EntityDeletionNotAllowedException {

        Category category= categoryRepositoryCommons.validateAndGetEntityOrNull(categoryId);
        categoryEntityValidator.validateEntityDeletable(category);
        categoryRepository.deleteById(categoryId);

        return category;
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
}
