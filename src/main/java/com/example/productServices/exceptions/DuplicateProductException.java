package com.example.productServices.exceptions;

public class DuplicateProductException extends Exception{

    public DuplicateProductException(String productName){
        super("Product "+productName+" already exist");
    }
}
