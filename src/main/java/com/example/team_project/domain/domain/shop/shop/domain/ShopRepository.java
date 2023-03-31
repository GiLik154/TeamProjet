package com.example.team_project.domain.domain.shop.shop.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ShopRepository extends JpaRepository<Shop,Long> {
    Shop findByShopName(String name);
}
