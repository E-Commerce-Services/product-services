package com.example.productServices.commons.exceptionthrower;

import com.example.productServices.exceptions.ProductByIdNotFoundException;
import com.example.productServices.models.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductExceptionThrower extends ExceptionThrower<Product> {
    @Override
    public void throwEntityByIdNotFoundException(Long id) throws ProductByIdNotFoundException {
        throw new ProductByIdNotFoundException(id);
    }

    @Override
    public void throwConstraintViolationException(Set<ConstraintViolation<Product>> violations) throws ConstraintViolationException {
        throw new ConstraintViolationException("Product Constraints are violating",violations);
    }
}
