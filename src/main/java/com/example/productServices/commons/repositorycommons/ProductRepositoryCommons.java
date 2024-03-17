package com.example.productServices.commons.repositorycommons;

import com.example.productServices.commons.validator.EntityValidator;
import com.example.productServices.commons.validator.ProductEntityValidator;
import com.example.productServices.models.Product;
import com.example.productServices.repository.BaseRepository;
import com.example.productServices.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductRepositoryCommons extends RepositoryCommons<Product> {

    public ProductRepositoryCommons(ProductEntityValidator productEntityValidator, ProductRepository productRepository) {
        super(productEntityValidator, productRepository);
    }
}
