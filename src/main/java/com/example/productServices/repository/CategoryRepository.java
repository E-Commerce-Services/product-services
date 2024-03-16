package com.example.productServices.repository;

import com.example.productServices.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsCategoriesById(Long categoryId);
    boolean existsCategoriesByName(String categoryName);
}
