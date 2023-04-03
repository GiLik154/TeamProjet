package com.example.team_project.domain.domain.shop.shop.domain;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface ShopRepository extends JpaRepository<Shop,Long> {
    Shop findByShopName(String name);


    Optional<Shop> findBySellerId(Long seller);
}
