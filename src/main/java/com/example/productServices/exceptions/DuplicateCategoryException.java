package com.example.productServices.exceptions;

public class DuplicateCategoryException extends Exception{

    public DuplicateCategoryException(String categoryName){
        super("Category "+categoryName+" already exist with");
    }
}
