package com.example.productServices.exceptions;

public class CategoryNotFoundException extends Exception{

    public CategoryNotFoundException(Long categoryId){
        super("Category with ID "+categoryId+" Not found");
    }
}
