package com.example.productServices.commons.exceptioncreator;

import com.example.productServices.exceptions.CategoryByIDNotFoundException;
import com.example.productServices.exceptions.EntityNotFoundException;
import com.example.productServices.models.Category;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryExceptionCreator extends ExceptionCreator<Category> {
    @Override
    public EntityNotFoundException createEntityByIDNotFoundException(Long id) {
        return new CategoryByIDNotFoundException(id);
    }

    @Override
    public ConstraintViolationException createConstraintViolationException(Set<ConstraintViolation<Category>> violations) {
        return new ConstraintViolationException("Category Constraints are violating",violations);
    }
}
