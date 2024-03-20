package com.example.productServices.controllers;

import com.example.productServices.dtos.client.CategoryManipulationRequestDTO;
import com.example.productServices.dtos.client.CategoryResponseDTO;
import com.example.productServices.dtos.client.CategoriesResponseDTO;
import com.example.productServices.dtos.server.CategoryManipulationMappedDTO;
import com.example.productServices.exceptions.*;
import com.example.productServices.models.Category;
import com.example.productServices.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryServices categoryServices;

    @Autowired
    public CategoryController(CategoryServices categoryServices){
        this.categoryServices=categoryServices;
    }

    @GetMapping
    public ResponseEntity<CategoriesResponseDTO> getAllCategories(){

        List<Category> categories=categoryServices.getAllCategory();

        return new ResponseEntity<>(
                new CategoriesResponseDTO(categories),
                HttpStatus.FOUND
        );
    }

    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @RequestBody CategoryManipulationRequestDTO categoryManipulationRequestDTO
    )
            throws DuplicateEntityException {

        Category category=categoryServices.createCategory(
                CategoryManipulationMappedDTO.fromClientDTO(categoryManipulationRequestDTO)
        );

        return new ResponseEntity<>(
                new CategoryResponseDTO(category),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{category_id}")
    public ResponseEntity<CategoryResponseDTO>  updateCategory(
            @PathVariable Long category_id,
            @RequestBody CategoryManipulationRequestDTO categoryManipulationRequestDTO
    ) throws DuplicateEntityException, EntityNotFoundException {

        Category updateCategory=categoryServices.modifyCategory(
                category_id,
                CategoryManipulationMappedDTO.fromClientDTO(categoryManipulationRequestDTO)
        );

        return new ResponseEntity<>(
                new CategoryResponseDTO(updateCategory),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<CategoryResponseDTO>  deleteCategory(
            @PathVariable Long category_id
    ) throws EntityDeletionNotAllowedException, EntityNotFoundException {

        Category deleteCategory=categoryServices.deleteCategory(category_id);

        return new ResponseEntity<>(
                new CategoryResponseDTO(deleteCategory),
                HttpStatus.OK
        );
    }
}
