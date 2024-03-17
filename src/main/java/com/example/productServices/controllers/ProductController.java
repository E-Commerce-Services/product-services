package com.example.productServices.controllers;

import com.example.productServices.dtos.client.ProductManipulationRequestDTO;
import com.example.productServices.dtos.client.ProductResponseDTO;
import com.example.productServices.dtos.server.ProductManipulationMappedDTO;
import com.example.productServices.exceptions.DuplicateEntityException;
import com.example.productServices.exceptions.DuplicateProductNameException;
import com.example.productServices.exceptions.EntityNotFoundException;
import com.example.productServices.models.Product;
import com.example.productServices.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServices productServices;

    @Autowired
    ProductController(ProductServices productServices){
        this.productServices=productServices;
    }


    @PostMapping()
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductManipulationRequestDTO productManipulationRequestDTO)
            throws DuplicateEntityException, EntityNotFoundException {

        Product product=productServices.createProduct(
                ProductManipulationMappedDTO.fromClientDTO(productManipulationRequestDTO)
        );

        return new ResponseEntity<>(
                new ProductResponseDTO(product),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{product_id}")
    public ResponseEntity<ProductResponseDTO> modifyProduct(
            @PathVariable Long product_id,
            @RequestBody ProductManipulationRequestDTO productManipulationRequestDTO
    ) throws DuplicateEntityException, EntityNotFoundException {

        Product product=productServices.modifyProductDetails(
                product_id,
                ProductManipulationMappedDTO.fromClientDTO(productManipulationRequestDTO)
        );

        return new ResponseEntity<>(
                new ProductResponseDTO(product),
                HttpStatus.OK
        );
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<ProductResponseDTO> replaceProduct(
            @PathVariable Long product_id,
            @RequestBody ProductManipulationRequestDTO productManipulationRequestDTO
    ) throws DuplicateEntityException, EntityNotFoundException {

        Product product=productServices.replaceProduct(
                product_id,
                ProductManipulationMappedDTO.fromClientDTO(productManipulationRequestDTO)
        );

        return new ResponseEntity<>(
                new ProductResponseDTO(product),
                HttpStatus.OK
        );
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long product_id) throws EntityNotFoundException {

        Product product=productServices.getProduct(product_id);

        return new ResponseEntity<>(
                new ProductResponseDTO(product),
                HttpStatus.OK
        );
    }



}
