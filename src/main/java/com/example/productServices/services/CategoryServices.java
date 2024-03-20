package com.example.productServices.services;

import com.example.productServices.commons.EntityValidator;
import com.example.productServices.dtos.server.CategoryManipulationMappedDTO;
import com.example.productServices.exceptions.*;
import com.example.productServices.models.Category;
import com.example.productServices.repository.CategoryRepository;
import com.example.productServices.repository.ProductRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServices {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryEntityValidator categoryEntityValidator;

    @Autowired
    public CategoryServices(CategoryRepository categoryRepository,
                            ProductRepository productRepository,
                            Validator validator){
        this.categoryRepository=categoryRepository;
        this.productRepository = productRepository;
        this.categoryEntityValidator = new CategoryEntityValidator(validator,categoryRepository);
    }

    private class CategoryEntityValidator extends EntityValidator<Category>{

        private CategoryEntityValidator(Validator validator, CategoryRepository categoryRepository) {

            super(validator, categoryRepository, CategoryByIDNotFoundException::new);
        }

        private void validateCategory(Category category) throws DuplicateCategoryNameException {
            categoryEntityValidator.validateEntityConstraints(category);

            if(category.getId()==null) validateCategoryNameUniqueness(category.getName());
            else validateCategoryNameUniqueness(category.getId(),category.getName());
        }

        private void validateCategoryNameUniqueness(String categoryName) throws DuplicateCategoryNameException {
            if(categoryRepository.existsByName(categoryName))
                throw new DuplicateCategoryNameException(categoryName);
        }

        private void validateCategoryNameUniqueness(Long categoryId,String categoryName) throws DuplicateCategoryNameException {
            if(categoryRepository.existsByIdNotAndName(categoryId,categoryName))
                throw new DuplicateCategoryNameException(categoryName);
        }

        private void validateCategoryDeletable(Long categoryId) throws CategoryInUseException {
            if(productRepository.existsProductByCategory_Id(categoryId))
                throw new CategoryInUseException(categoryId);
        }
    }


    private Category buildCategory(CategoryManipulationMappedDTO categoryManipulationMappedDTO)  {
        return Category.builder()
                .name(categoryManipulationMappedDTO.name())
                .build();
    }

    public Category createCategory(CategoryManipulationMappedDTO categoryManipulationMappedDTO) throws DuplicateEntityException {

        Category newCategory= buildCategory(categoryManipulationMappedDTO);

        categoryEntityValidator.validateCategory(newCategory);

        return categoryRepository.save(newCategory);
    }

    public Category modifyCategory(Long categoryId, CategoryManipulationMappedDTO categoryManipulationMappedDTO)
            throws DuplicateEntityException, EntityNotFoundException {

        Category modifiedCategory= categoryEntityValidator.validateEntityExistenceAndReturn(categoryId);

        if(categoryManipulationMappedDTO.name()!=null)
            modifiedCategory.setName(categoryManipulationMappedDTO.name());

        categoryEntityValidator.validateCategory(modifiedCategory);

        return categoryRepository.save(modifiedCategory);
    }

    public Category deleteCategory(Long categoryId) throws EntityNotFoundException, EntityDeletionNotAllowedException {

        Category category= categoryEntityValidator.validateEntityExistenceAndReturn(categoryId);

        categoryEntityValidator.validateCategoryDeletable(categoryId);
        categoryRepository.deleteById(categoryId);

        return category;
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
}
