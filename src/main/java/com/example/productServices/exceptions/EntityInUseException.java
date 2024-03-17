package com.example.productServices.exceptions;

abstract public class EntityInUseException extends EntityDeletionNotAllowedException{
    public EntityInUseException(String message,String entityType) {
        super(message,entityType);
    }
}
