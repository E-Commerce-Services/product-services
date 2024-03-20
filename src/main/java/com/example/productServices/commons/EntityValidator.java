package com.example.productServices.commons;

import com.example.productServices.exceptions.EntityNotFoundException;
import com.example.productServices.models.BaseModal;
import com.example.productServices.repository.BaseRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Optional;
import java.util.Set;

public class EntityValidator<T extends BaseModal> {

    private final Validator validator;
    private final BaseRepository<T> baseRepository;
    private final EntityNotFoundExceptionProvider exceptionFactory;

    public EntityValidator(Validator validator, BaseRepository<T> baseRepository, EntityNotFoundExceptionProvider exceptionFactory) {
        this.validator = validator;
        this.baseRepository = baseRepository;
        this.exceptionFactory = exceptionFactory;
    }

    public void validateEntityConstraints(T entity) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(entity.getClass().getSimpleName()+" Constraints are violating",violations);

    }

    public T validateEntityExistenceAndReturn(Long id) throws EntityNotFoundException {
        Optional<T> optionalEntity=baseRepository.findById(id);

        if(optionalEntity.isEmpty())
            throw exceptionFactory.createEntityNotFoundException(id);

        return optionalEntity.get();
    }

}
