package com.example.productServices.exceptions;

public class CategoryInUseException extends EntityInUseException{

    public CategoryInUseException(Long categoryId) {
        super("Category With ID: "+categoryId+" used by other entities","Category");
    }
}
