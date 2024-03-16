package com.example.productServices.exceptions;

public class ProductNotFoundException extends Exception{

    public ProductNotFoundException(Long id){
        super("Product with ID "+id+" Not found");
    }
}
