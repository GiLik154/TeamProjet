package com.example.team_project.domain.domain.product.product.domain;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.exception.OrderNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    default Product validateProductId(Long productId){
        Optional<Product> productOptional = findById(productId);
        if (productOptional.isPresent()){
            return productOptional.get();
        }
        throw new OrderNotFoundException();
    }

    Optional<Product> findBySeller(Seller seller);

    default Product validateSellerId(Seller seller){
        Optional<Product> productOptional = findBySeller(seller);
        if (productOptional.isPresent()){
            return productOptional.get();
        }
        throw new UsernameNotFoundException("Invalid sellerID");
    }

    Optional<Product> findByName(String name);

}
