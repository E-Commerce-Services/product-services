package com.example.productServices.commons.exceptionthrower;

import com.example.productServices.exceptions.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;

abstract public class ExceptionThrower<T> {
    abstract public void throwEntityByIdNotFoundException(Long id) throws EntityNotFoundException;
    abstract public void throwConstraintViolationException(Set<ConstraintViolation<T>> violations) throws ConstraintViolationException;
}
