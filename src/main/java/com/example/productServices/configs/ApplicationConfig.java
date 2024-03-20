package com.example.productServices.configs;

import com.example.productServices.commons.EntityValidator;
import com.example.productServices.exceptions.CategoryByIDNotFoundException;
import com.example.productServices.exceptions.ProductByIdNotFoundException;
import com.example.productServices.models.Category;
import com.example.productServices.models.Product;
import com.example.productServices.repository.CategoryRepository;
import com.example.productServices.repository.ProductRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    private final Validator validator;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ApplicationConfig(Validator validator,
                             ProductRepository productRepository,
                             CategoryRepository categoryRepository){
        this.validator=validator;
        this.productRepository=productRepository;
        this.categoryRepository=categoryRepository;
    }

    @Bean
    public EntityValidator<Product> productEntityValidator(){
        return new EntityValidator<>(validator,productRepository, ProductByIdNotFoundException::new);
    }

    @Bean
    public EntityValidator<Category> categoryEntityValidator(){
        return new EntityValidator<>(validator,categoryRepository, CategoryByIDNotFoundException::new);
    }

}
