package com.example.team_project.domain.domain.product.domain.stock;

import com.example.team_project.domain.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
}
