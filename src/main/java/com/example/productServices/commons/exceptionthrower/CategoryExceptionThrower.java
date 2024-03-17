package com.example.productServices.commons.exceptionthrower;

import com.example.productServices.exceptions.CategoryByIDNotFoundException;
import com.example.productServices.models.Category;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryExceptionThrower extends ExceptionThrower<Category> {
    @Override
    public void throwEntityByIdNotFoundException(Long id) throws CategoryByIDNotFoundException {
        throw new CategoryByIDNotFoundException(id);
    }

    @Override
    public void throwConstraintViolationException(Set<ConstraintViolation<Category>> violations) throws ConstraintViolationException {
        throw new ConstraintViolationException("Category Constraints are violating",violations);
    }
}
