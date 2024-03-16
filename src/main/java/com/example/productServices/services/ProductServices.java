package com.example.productServices.services;

import com.example.productServices.dtos.server.ProductManipulationMappedDTO;
import com.example.productServices.exceptions.CategoryNotFoundException;
import com.example.productServices.exceptions.DuplicateProductException;
import com.example.productServices.exceptions.ProductNotFoundException;
import com.example.productServices.models.Product;
import com.example.productServices.repository.ProductRepository;
import com.example.productServices.commons.DBCommons;
import com.example.productServices.commons.ValidationCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServices {

    private final ProductRepository productRepository;
    private final DBCommons dbCommons;
    private final ValidationCommons validationCommons;

    @Autowired
    public ProductServices(ProductRepository productRepository,
                           DBCommons dbCommons, ValidationCommons validationCommons){
        this.productRepository=productRepository;
        this.dbCommons = dbCommons;
        this.validationCommons = validationCommons;
    }

    private Product buildAndValidateProduct(ProductManipulationMappedDTO productManipulationMappedDTO)
            throws CategoryNotFoundException, DuplicateProductException {

        Product product=Product.builder()
                .name(productManipulationMappedDTO.name())
                .price(productManipulationMappedDTO.price())
                .description(productManipulationMappedDTO.description())
                .quantity(productManipulationMappedDTO.quantity())
                .category(dbCommons.getCategoryOrNull(productManipulationMappedDTO.categoryId()))
                .build();

        validationCommons.validateEntity(product);

        return product;
    }


    public Product createProduct(ProductManipulationMappedDTO productManipulationMappedDTO)
            throws CategoryNotFoundException, DuplicateProductException {

        Product newProduct= buildAndValidateProduct(productManipulationMappedDTO);

        return productRepository.save(newProduct);

    }

    public Product modifyProductDetails(Long productId, ProductManipulationMappedDTO productManipulationMappedDTO)
            throws CategoryNotFoundException, ProductNotFoundException, DuplicateProductException {

        Product existingProduct = dbCommons.getProductOrNull(productId);

        if(productManipulationMappedDTO.name()!=null) existingProduct.setName(productManipulationMappedDTO.name());
        if(productManipulationMappedDTO.categoryId()!=null) existingProduct.setCategory(
                dbCommons.getCategoryOrNull(productManipulationMappedDTO.categoryId())
        );
        if(productManipulationMappedDTO.price()!=null) existingProduct.setPrice(productManipulationMappedDTO.price());
        if(productManipulationMappedDTO.description()!=null) existingProduct.setDescription(productManipulationMappedDTO.description());
        if(productManipulationMappedDTO.quantity()!=null) existingProduct.setQuantity(productManipulationMappedDTO.quantity());

        validationCommons.validateEntity(existingProduct);

        return productRepository.save(existingProduct);
    }

    public Product replaceProduct(Long productId, ProductManipulationMappedDTO productManipulationMappedDTO)
            throws CategoryNotFoundException, ProductNotFoundException, DuplicateProductException {

        validationCommons.validateProductExistence(productId);

        Product replacedProduct= buildAndValidateProduct(productManipulationMappedDTO);
        replacedProduct.setId(productId);

        return productRepository.save(replacedProduct);
    }

    public Product getProduct(Long productId) throws ProductNotFoundException {
        return dbCommons.getProductOrNull(productId);
    }

}
