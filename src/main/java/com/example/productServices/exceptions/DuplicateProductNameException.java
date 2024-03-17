package com.example.productServices.exceptions;

public class DuplicateProductNameException extends DuplicateEntityException{
    public DuplicateProductNameException(String productName) {
        super("Product '" +productName+ "' already exist","Product","Name");
    }
}
