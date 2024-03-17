package com.example.productServices.exceptions;

public class DuplicateCategoryNameException extends DuplicateEntityException{
    public DuplicateCategoryNameException(String categoryName) {
        super("Category '" +categoryName+ "' already exist","Category","Name");
    }
}
