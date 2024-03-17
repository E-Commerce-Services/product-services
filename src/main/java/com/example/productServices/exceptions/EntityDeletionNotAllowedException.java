package com.example.productServices.exceptions;

import lombok.Getter;

@Getter
abstract public class EntityDeletionNotAllowedException extends EntityException{
    private final String entityType;
    public EntityDeletionNotAllowedException(String message,String entityType) {
        super(message);
        this.entityType=entityType;
    }
}
