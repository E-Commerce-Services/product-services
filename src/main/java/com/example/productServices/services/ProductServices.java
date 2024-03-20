package com.example.productServices.services;

import com.example.productServices.commons.EntityValidator;
import com.example.productServices.dtos.server.ProductManipulationMappedDTO;
import com.example.productServices.exceptions.DuplicateEntityException;
import com.example.productServices.exceptions.DuplicateProductNameException;
import com.example.productServices.exceptions.EntityNotFoundException;
import com.example.productServices.exceptions.ProductByIdNotFoundException;
import com.example.productServices.models.Category;
import com.example.productServices.models.Product;
import com.example.productServices.repository.BaseRepository;
import com.example.productServices.repository.ProductRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServices {

    private final EntityValidator<Category> categoryEntityValidator;
    private final ProductEntityValidator productEntityValidator;
    private final ProductRepository productRepository;

    @Autowired
    public ProductServices(EntityValidator<Category> categoryEntityValidator,
                           ProductRepository productRepository,
                           Validator validator){
        this.categoryEntityValidator = categoryEntityValidator;
        this.productEntityValidator= new ProductEntityValidator(validator, productRepository);
        this.productRepository=productRepository;
    }

    private class ProductEntityValidator extends EntityValidator<Product>{

        public ProductEntityValidator(Validator validator, BaseRepository<Product> baseRepository) {
            super(validator, baseRepository, ProductByIdNotFoundException::new);
        }

        private void validateProduct(Product product) throws DuplicateProductNameException {

            productEntityValidator.validateEntityConstraints(product);

            if(product.getId()==null) validateProductNameUniqueness(product.getName());
            else validateProductNameUniqueness(product.getId(),product.getName());

        }

        private void validateProductNameUniqueness(String productName) throws DuplicateProductNameException {
            if(productRepository.existsProductByName(productName))
                throw new DuplicateProductNameException(productName);
        }

        private void validateProductNameUniqueness(Long productId,String productName) throws DuplicateProductNameException {
            if(productRepository.existsByIdNotAndName(productId,productName))
                throw new DuplicateProductNameException(productName);
        }
    }

    private Product buildProduct(ProductManipulationMappedDTO productManipulationMappedDTO)
            throws EntityNotFoundException {

        Category category=null;

        if(productManipulationMappedDTO.categoryId()!=null) {
            category=categoryEntityValidator.validateEntityExistenceAndReturn(productManipulationMappedDTO.categoryId());
        }

         return Product.builder()
                .name(productManipulationMappedDTO.name())
                .price(productManipulationMappedDTO.price())
                .description(productManipulationMappedDTO.description())
                .category(category)
                .build();
    }

    public Product createProduct(ProductManipulationMappedDTO productManipulationMappedDTO)
            throws DuplicateEntityException, EntityNotFoundException {

        Product newProduct= buildProduct(productManipulationMappedDTO);

        productEntityValidator.validateProduct(newProduct);
        return productRepository.save(newProduct);

    }

    public Product modifyProductDetails(Long productId, ProductManipulationMappedDTO productManipulationMappedDTO)
            throws EntityNotFoundException, DuplicateEntityException {

        Product existingProduct = productEntityValidator.validateEntityExistenceAndReturn(productId);

        if(productManipulationMappedDTO.name()!=null)
            existingProduct.setName(productManipulationMappedDTO.name());

        if(productManipulationMappedDTO.categoryId()!=null){
            Category category=categoryEntityValidator.validateEntityExistenceAndReturn(productManipulationMappedDTO.categoryId());
            existingProduct.setCategory(category);
        }

        if(productManipulationMappedDTO.price()!=null)
            existingProduct.setPrice(productManipulationMappedDTO.price());

        if(productManipulationMappedDTO.description()!=null)
            existingProduct.setDescription(productManipulationMappedDTO.description());

        productEntityValidator.validateProduct(existingProduct);
        return productRepository.save(existingProduct);
    }

    public Product replaceProduct(Long productId, ProductManipulationMappedDTO productManipulationMappedDTO)
            throws EntityNotFoundException, DuplicateEntityException {

        productEntityValidator.validateEntityExistenceAndReturn(productId);

        Product replacedProduct= buildProduct(productManipulationMappedDTO);
        replacedProduct.setId(productId);

        productEntityValidator.validateProduct(replacedProduct);
        return productRepository.save(replacedProduct);
    }

    public Product getProduct(Long productId) throws EntityNotFoundException {
        return productEntityValidator.validateEntityExistenceAndReturn(productId);
    }

}
