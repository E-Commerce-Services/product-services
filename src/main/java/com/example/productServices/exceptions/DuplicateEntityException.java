package com.example.productServices.exceptions;

import lombok.Getter;

@Getter
abstract public class DuplicateEntityException extends EntityException{
    private final String entityType,field;
    public DuplicateEntityException(String message,String entityType,String field) {
        super(message);
        this.entityType=entityType;
        this.field=field;
    }
}
