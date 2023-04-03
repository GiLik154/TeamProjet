package com.example.team_project.domain.domain.product.category.domain;

import com.example.team_project.enums.ProductCategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
    Optional<ProductCategory> findByStatus(ProductCategoryStatus status);
}
