package com.example.productServices.commons.validator;

import com.example.productServices.commons.exceptioncreator.ExceptionCreator;
import com.example.productServices.exceptions.DuplicateEntityException;
import com.example.productServices.exceptions.EntityNotFoundException;
import com.example.productServices.repository.BaseRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Optional;
import java.util.Set;

abstract public class EntityValidator<T> {

    private final Validator validator;
    private final BaseRepository<T> baseRepository;
    private final ExceptionCreator<T> exceptionCreator;

    public EntityValidator(Validator validator, BaseRepository<T> baseRepository, ExceptionCreator<T> exceptionCreator) {
        this.validator = validator;
        this.baseRepository = baseRepository;
        this.exceptionCreator = exceptionCreator;
    }

    public void validateEntityConstraints(T entity) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        if (!violations.isEmpty())
            throw exceptionCreator.createConstraintViolationException(violations);

    }

    public T validateEntityExistenceAndReturn(Long id) throws EntityNotFoundException {
        Optional<T> optionalEntity=baseRepository.findById(id);

        if(optionalEntity.isEmpty())
            throw exceptionCreator.createEntityByIDNotFoundException(id);

        return optionalEntity.get();
    }

    abstract public void validateEntity(T entity) throws DuplicateEntityException;
}
