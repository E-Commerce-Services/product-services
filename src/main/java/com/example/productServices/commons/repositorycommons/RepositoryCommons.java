package com.example.productServices.commons.repositorycommons;

import com.example.productServices.commons.validator.EntityValidator;
import com.example.productServices.exceptions.DuplicateEntityException;
import com.example.productServices.exceptions.EntityNotFoundException;
import com.example.productServices.repository.BaseRepository;

abstract public class RepositoryCommons<T> {

    private final EntityValidator<T> entityValidator;
    private final BaseRepository<T> baseRepository;


    public RepositoryCommons(EntityValidator<T> entityValidator,BaseRepository<T> baseRepository) {
        this.entityValidator=entityValidator;
        this.baseRepository=baseRepository;
    }

    public T validateAndGetEntityOrNull(Long entityId) throws EntityNotFoundException {
        if(entityId==null) return null;
        return entityValidator.validateEntityExistenceAndReturn(entityId);
    }

    public T validateAndSaveEntity(T entity) throws DuplicateEntityException {
        entityValidator.validateEntity(entity);
        return baseRepository.save(entity);
    }
}
