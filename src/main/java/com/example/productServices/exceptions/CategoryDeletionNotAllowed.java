package com.example.productServices.exceptions;

public class CategoryDeletionNotAllowed extends Exception{

    public CategoryDeletionNotAllowed(Long categoryId){
        super("Category With ID: "+categoryId+" used by other entities");
    }
}
