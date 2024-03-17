package com.example.productServices.services;

import com.example.productServices.commons.repositorycommons.CategoryRepositoryCommons;
import com.example.productServices.commons.repositorycommons.ProductRepositoryCommons;
import com.example.productServices.commons.validator.ProductEntityValidator;
import com.example.productServices.dtos.server.ProductManipulationMappedDTO;
import com.example.productServices.exceptions.DuplicateEntityException;
import com.example.productServices.exceptions.EntityNotFoundException;
import com.example.productServices.models.Product;
import com.example.productServices.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServices {

    private final ProductRepository productRepository;
    private final CategoryRepositoryCommons categoryRepositoryCommons;
    private final ProductRepositoryCommons productRepositoryCommons;
    private final ProductEntityValidator productEntityValidator;

    @Autowired
    public ProductServices(ProductRepository productRepository,
                           CategoryRepositoryCommons categoryRepositoryCommons,
                           ProductRepositoryCommons productRepositoryCommons,
                           ProductEntityValidator productEntityValidator){
        this.productRepository=productRepository;
        this.categoryRepositoryCommons = categoryRepositoryCommons;
        this.productRepositoryCommons=productRepositoryCommons;
        this.productEntityValidator = productEntityValidator;
    }

    private Product buildProduct(ProductManipulationMappedDTO productManipulationMappedDTO)
            throws EntityNotFoundException {

         return Product.builder()
                .name(productManipulationMappedDTO.name())
                .price(productManipulationMappedDTO.price())
                .description(productManipulationMappedDTO.description())
                .quantity(productManipulationMappedDTO.quantity())
                .category(categoryRepositoryCommons.validateAndGetEntityOrNull(productManipulationMappedDTO.categoryId()))
                .build();

    }


    public Product createProduct(ProductManipulationMappedDTO productManipulationMappedDTO)
            throws DuplicateEntityException, EntityNotFoundException {

        Product newProduct= buildProduct(productManipulationMappedDTO);

        return productRepositoryCommons.validateAndSaveEntity(newProduct);

    }

    public Product modifyProductDetails(Long productId, ProductManipulationMappedDTO productManipulationMappedDTO)
            throws EntityNotFoundException, DuplicateEntityException {

        Product existingProduct = productEntityValidator.validateEntityExistenceAndReturn(productId);

        if(productManipulationMappedDTO.name()!=null) existingProduct.setName(productManipulationMappedDTO.name());
        if(productManipulationMappedDTO.categoryId()!=null) existingProduct.setCategory(
                categoryRepositoryCommons.validateAndGetEntityOrNull(productManipulationMappedDTO.categoryId())
        );
        if(productManipulationMappedDTO.price()!=null) existingProduct.setPrice(productManipulationMappedDTO.price());
        if(productManipulationMappedDTO.description()!=null) existingProduct.setDescription(productManipulationMappedDTO.description());
        if(productManipulationMappedDTO.quantity()!=null) existingProduct.setQuantity(productManipulationMappedDTO.quantity());

        return productRepositoryCommons.validateAndSaveEntity(existingProduct);
    }

    public Product replaceProduct(Long productId, ProductManipulationMappedDTO productManipulationMappedDTO)
            throws EntityNotFoundException, DuplicateEntityException {

        productEntityValidator.validateEntityExistenceAndReturn(productId);

        Product replacedProduct= buildProduct(productManipulationMappedDTO);
        replacedProduct.setId(productId);

        return productRepositoryCommons.validateAndSaveEntity(replacedProduct);
    }

    public Product getProduct(Long productId) throws EntityNotFoundException {
        return productRepositoryCommons.validateAndGetEntityOrNull(productId);
    }

}
