package com.example.productServices.dtos.client;

import com.example.productServices.models.Category;

import java.util.List;

public record CategoriesResponseDTO(List<Category> categories) {
}
