package com.example.productServices.commons;

import com.example.productServices.exceptions.*;
import com.example.productServices.models.Category;
import com.example.productServices.models.Product;
import com.example.productServices.repository.CategoryRepository;
import com.example.productServices.repository.ProductRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Set;

@Component
public class ValidationCommons {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final Validator validator;

    @Autowired
    public ValidationCommons(CategoryRepository categoryRepository, ProductRepository productRepository, Validator validator) {
        this.categoryRepository = categoryRepository;
        this.validator = validator;
        this.productRepository = productRepository;
    }

    private void validateProductNameUniqueness(String productName) throws DuplicateProductException {
        if(productRepository.existsProductByName(productName))
            throw new DuplicateProductException(productName);
    }

    private void validateCategoryNameUniqueness(String categoryName) throws  DuplicateCategoryException {
        if(categoryRepository.existsCategoriesByName(categoryName))
            throw new DuplicateCategoryException(categoryName);
    }

    private void validateConstraints(Product product){
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty())
            throw new ConstraintViolationException("Product Constraints are violating", violations);
    }

    private void validateConstraints(Category category){
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        if (!violations.isEmpty())
            throw new ConstraintViolationException("Category Constraints are violating", violations);
    }


    public void validateEntity(Product product) throws DuplicateProductException {
        this.validateConstraints(product);
        this.validateProductNameUniqueness(product.getName());
    }

    public void validateEntity(Category category) throws DuplicateCategoryException {
        this.validateConstraints(category);
        this.validateCategoryNameUniqueness(category.getName());
    }

    public void validateProductExistence(Long productId) throws ProductNotFoundException {
        if(productRepository.existsProductById(productId))
            throw new ProductNotFoundException(productId);
    }

    public void validateCategoryExistence(Long categoryId) throws CategoryNotFoundException {
        if(categoryRepository.existsCategoriesById(categoryId))
            throw new CategoryNotFoundException(categoryId);
    }

    public void validateCategoryDeletable(Long categoryId) throws CategoryDeletionNotAllowed {
        if(productRepository.existsProductByCategory_Id(categoryId))
            throw new CategoryDeletionNotAllowed(categoryId);
    }
}
