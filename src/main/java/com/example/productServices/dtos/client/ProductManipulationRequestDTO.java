package com.example.productServices.dtos.client;

public record ProductManipulationRequestDTO(String name, Long categoryId, Double price, String description, Integer quantity){}
