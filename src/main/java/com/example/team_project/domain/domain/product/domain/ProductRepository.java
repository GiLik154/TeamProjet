package com.example.team_project.domain.domain.product.domain;

import com.example.team_project.exception.OrderNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    default Product validateProductId(Long productId){
        Optional<Product> productOptional = findById(productId);
        if (productOptional.isPresent()){
            return productOptional.get();
        }
        throw new OrderNotFoundException();
    }
}
