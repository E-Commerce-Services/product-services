package com.example.productServices.dtos.server;

import com.example.productServices.dtos.client.CategoryManipulationRequestDTO;

public record CategoryManipulationMappedDTO(String name) {

    public static CategoryManipulationMappedDTO fromClientDTO(CategoryManipulationRequestDTO categoryManipulationRequestDTO) {
        return new CategoryManipulationMappedDTO(
                categoryManipulationRequestDTO.name()
        );
    }
}
