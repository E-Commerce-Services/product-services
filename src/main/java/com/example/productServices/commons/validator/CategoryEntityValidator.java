package com.example.productServices.commons.validator;

import com.example.productServices.commons.exceptionthrower.CategoryExceptionThrower;
import com.example.productServices.exceptions.*;
import com.example.productServices.models.Category;
import com.example.productServices.repository.CategoryRepository;
import com.example.productServices.repository.ProductRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryEntityValidator extends EntityValidator<Category> {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryEntityValidator(CategoryRepository categoryRepository, ProductRepository productRepository, Validator validator, CategoryExceptionThrower categoryExceptionThrower) {
        super(validator,categoryRepository,categoryExceptionThrower);
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public void validateEntityFieldsUniqueness(Category category) throws DuplicateEntityException {
        if(category.getId()!=null){
            if(categoryRepository.existsByIdNotAndName(category.getId(),category.getName()))
                throw new DuplicateCategoryNameException(category.getName());
        }
        else {
            if(categoryRepository.existsByName(category.getName()))
                throw new DuplicateCategoryNameException(category.getName());
        }
    }

    public void validateEntityDeletable(Category category) throws EntityDeletionNotAllowedException {
        if(productRepository.existsProductByCategory_Id(category.getId()))
            throw new CategoryInUseException(category.getId());
    }

    @Override
    public void validateEntity(Category category) throws DuplicateEntityException {
        validateEntityConstraints(category);
        validateEntityFieldsUniqueness(category);
    }
}
