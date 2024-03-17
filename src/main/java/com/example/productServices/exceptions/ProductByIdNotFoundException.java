package com.example.productServices.exceptions;

public class ProductByIdNotFoundException extends EntityNotFoundException{
    public ProductByIdNotFoundException(Long productId) {
        super("Product with ID "+productId+" Not found","Product");
    }
}
