package com.example.productServices.dtos.server;

import com.example.productServices.dtos.client.ProductManipulationRequestDTO;

public record ProductManipulationMappedDTO(String name, Long categoryId, Double price, String description) {

    public static ProductManipulationMappedDTO fromClientDTO(ProductManipulationRequestDTO productManipulationRequestDTO) {
        return new ProductManipulationMappedDTO(
                productManipulationRequestDTO.name(),
                productManipulationRequestDTO.categoryId(),
                productManipulationRequestDTO.price(),
                productManipulationRequestDTO.description()
        );
    }
}
