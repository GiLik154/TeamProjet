package com.example.team_project.domain.domain.shop.seller.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findByOwnerName(String name);
    Optional<Seller> findById(Long sellerId);

}
