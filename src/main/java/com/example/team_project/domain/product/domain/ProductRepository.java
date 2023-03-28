package com.example.team_project.domain.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
