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
    public ResponseEntity<ExceptionDTO> handleEntityNotFoundException(EntityNotFoundException exception){
        String entityType=exception.getEntityType();

        ExceptionDTO exceptionDTO=new ExceptionDTO(
                exception.getMessage(),
                "The " + entityType.toLowerCase() + " you requested could not be found. Please try with different "+entityType.toLowerCase()
                );

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleDuplicateEntityException(DuplicateEntityException exception){
        String entityType=exception.getEntityType();
        String field=exception.getField();

        ExceptionDTO exceptionDTO=new ExceptionDTO(
                exception.getMessage(),
                "The " + entityType.toLowerCase() + " with same "+field.toLowerCase()+" already exist, "+"Please use a different "+field.toLowerCase()
        );

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleEntityDeletionNotAllowed(EntityDeletionNotAllowedException exception){
        ExceptionDTO exceptionDTO=new ExceptionDTO(
                exception.getMessage(),
                "The Deletion of "+exception.getEntityType()+" you requested cannot be processed"
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
