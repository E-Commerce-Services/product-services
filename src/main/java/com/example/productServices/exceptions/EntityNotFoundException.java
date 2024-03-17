package com.example.productServices.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends EntityException{
    private final String entityType;

    public EntityNotFoundException(String message,String entityType) {
        super(message);
        this.entityType=entityType;
    }
}
