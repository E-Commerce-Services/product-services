package com.example.productServices.repository;

import com.example.productServices.models.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {

    boolean existsByName(String categoryName);
    boolean existsByIdNotAndName(Long Id,String name);
}
