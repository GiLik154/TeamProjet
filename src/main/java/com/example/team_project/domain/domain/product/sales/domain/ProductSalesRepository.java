package com.example.team_project.domain.domain.product.sales.domain;

import com.example.team_project.domain.domain.product.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSalesRepository extends JpaRepository<ProductSales, Long> {
}
