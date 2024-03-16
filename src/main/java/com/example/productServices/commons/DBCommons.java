package com.example.productServices.commons;

import com.example.productServices.exceptions.CategoryNotFoundException;
import com.example.productServices.exceptions.ProductNotFoundException;
import com.example.productServices.models.Category;
import com.example.productServices.models.Product;
import com.example.productServices.repository.CategoryRepository;
import com.example.productServices.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DBCommons {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DBCommons(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public Product getProductOrNull(Long productId) throws ProductNotFoundException {
        if(productId==null) return null;

        Optional<Product> optionalProduct=productRepository.findById(productId);
        if(optionalProduct.isEmpty())
            throw new ProductNotFoundException(productId);

        return optionalProduct.get();
    }

    public Category getCategoryOrNull(Long categoryId) throws CategoryNotFoundException {
        if(categoryId==null) return null;

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty())
            throw new CategoryNotFoundException(categoryId);

        return optionalCategory.get();
    }


}
