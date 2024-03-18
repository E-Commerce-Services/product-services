package com.example.productServices.commons.exceptioncreator;

import com.example.productServices.exceptions.EntityNotFoundException;
import com.example.productServices.exceptions.ProductByIdNotFoundException;
import com.example.productServices.models.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductExceptionCreator extends ExceptionCreator<Product> {
    @Override
    public EntityNotFoundException createEntityByIDNotFoundException(Long id)  {
        return new ProductByIdNotFoundException(id);
    }

    @Override
    public ConstraintViolationException createConstraintViolationException(Set<ConstraintViolation<Product>> violations)  {
        return new ConstraintViolationException("Product Constraints are violating",violations);
    }
}
