package com.example.productServices.commons.validator;

import com.example.productServices.commons.exceptionthrower.ExceptionThrower;
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
    private final ExceptionThrower<T> exceptionThrower;

    public EntityValidator(Validator validator, BaseRepository<T> baseRepository, ExceptionThrower<T> exceptionThrower) {
        this.validator = validator;
        this.baseRepository = baseRepository;
        this.exceptionThrower=exceptionThrower;
    }

    public void validateEntityConstraints(T entity) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            exceptionThrower.throwConstraintViolationException(violations);
        }
    }

    public T validateEntityExistenceAndReturn(Long id) throws EntityNotFoundException {
        Optional<T> optionalEntity=baseRepository.findById(id);

        if(optionalEntity.isEmpty()){
            exceptionThrower.throwEntityByIdNotFoundException(id);
            return null;
        }

        return optionalEntity.get();
    }

    abstract public void validateEntity(T entity) throws DuplicateEntityException;
}
