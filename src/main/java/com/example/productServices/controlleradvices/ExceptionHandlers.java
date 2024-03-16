package com.example.productServices.controlleradvices;

import com.example.productServices.dtos.client.ExceptionDTO;
import com.example.productServices.exceptions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException){
        ExceptionDTO exceptionDTO=new ExceptionDTO(
                categoryNotFoundException.getMessage(),
                "Check the category ID. It probably doesn't exist."
                );

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleDuplicateProductException(DuplicateProductException duplicateProductException){
        ExceptionDTO exceptionDTO=new ExceptionDTO(
                duplicateProductException.getMessage(),
                "If you want to create another product, you need to change the name of the product"
        );

        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleDuplicateCategoryException(DuplicateCategoryException duplicateCategoryException){
        ExceptionDTO exceptionDTO=new ExceptionDTO(
                duplicateCategoryException.getMessage(),
                "If you want to create another Category, you need to change the name of the category"
        );

        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleProductNotFoundException(ProductNotFoundException productNotFoundException){
        ExceptionDTO exceptionDTO=new ExceptionDTO(
                productNotFoundException.getMessage(),
                "Product with specified ID might be deleted (or) not yet created"
        );

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleCategoryDeletionNotAllowed(CategoryDeletionNotAllowed categoryDeletionNotAllowed){
        ExceptionDTO exceptionDTO=new ExceptionDTO(
                categoryDeletionNotAllowed.getMessage(),
                "Please make sure none of the other entities are using this category and try again"
        );

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleConstraintViolationException(ConstraintViolationException productNotFoundException){

        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : productNotFoundException.getConstraintViolations()) {
            sb.append(constraintViolation.getMessage()).append(", ");
        }

        ExceptionDTO exceptionDTO=new ExceptionDTO(
                productNotFoundException.getMessage(),
                sb.toString()
        );

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }
}
