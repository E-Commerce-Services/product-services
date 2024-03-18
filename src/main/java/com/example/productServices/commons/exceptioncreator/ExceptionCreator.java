package com.example.productServices.commons.exceptioncreator;

import com.example.productServices.exceptions.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;

abstract public class ExceptionCreator<T> {
    abstract public EntityNotFoundException createEntityByIDNotFoundException(Long id);
    abstract public ConstraintViolationException createConstraintViolationException(Set<ConstraintViolation<T>> violations) ;
}
