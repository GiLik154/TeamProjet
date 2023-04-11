package com.example.team_project.domain.domain.product.product.domain;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    default Product validateProductId(Long productId){
        Optional<Product> productOptional = findById(productId);
        if (productOptional.isPresent()){
            return productOptional.get();
        }
        throw new ProductNotFoundException();
    }

    Product findBySellerId (Long sellerId);

    Optional<Product> findBySeller(Seller seller);
    Page<Product> findBySellerId(Long sellerId, PageRequest id);

    default Product validateSellerId(Seller seller){
        Optional<Product> productOptional = findBySeller(seller);
        if (productOptional.isPresent()){
            return productOptional.get();
        }
        throw new UsernameNotFoundException("Invalid sellerID");
    }

    Optional<Product> findByName(String name);


    Page<Product> findByCategoryId(Long categoryId,PageRequest id);
    Page<Product> findBySellerIdAndCategoryId(Long seller,Long categoryId,PageRequest id);
}
