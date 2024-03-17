package com.example.productServices.exceptions;

public class CategoryByIDNotFoundException extends EntityNotFoundException{
    public CategoryByIDNotFoundException(Long categoryId) {
        super("Category with ID "+categoryId+" Not found","Category");
    }
}
