package com.example.productServices.commons;

import com.example.productServices.exceptions.EntityNotFoundException;

public interface EntityNotFoundExceptionProvider {
    EntityNotFoundException createEntityNotFoundException(Long id);
}
