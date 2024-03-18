package com.example.productServices.commons.validator;

import com.example.productServices.commons.exceptioncreator.ProductExceptionCreator;
import com.example.productServices.exceptions.DuplicateEntityException;
import com.example.productServices.exceptions.DuplicateProductNameException;
import com.example.productServices.models.Product;
import com.example.productServices.repository.ProductRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductEntityValidator extends EntityValidator<Product> {
    private final ProductRepository productRepository;

    @Autowired
    public ProductEntityValidator(ProductExceptionCreator productExceptionThrower, ProductRepository productRepository, Validator validator) {
        super(validator, productRepository,productExceptionThrower);
        this.productRepository = productRepository;
    }

    public void validateProductFieldsUniqueness(Product product) throws DuplicateEntityException {
        if(product.getId()!=null){
            if(productRepository.existsByIdNotAndName(product.getId(),product.getName()))
                throw new DuplicateProductNameException(product.getName());
        }
        else {
            if(productRepository.existsProductByName(product.getName()))
                throw new DuplicateProductNameException(product.getName());
        }
    }


    @Override
    public void validateEntity(Product product) throws DuplicateEntityException {
        validateEntityConstraints(product);
        validateProductFieldsUniqueness(product);
    }
}
