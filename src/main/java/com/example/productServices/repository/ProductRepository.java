package com.example.productServices.repository;

import com.example.productServices.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsProductByCategory_Id(Long categoryId);

    boolean existsProductById(Long productId);

    boolean existsProductByName(String productName);
}
