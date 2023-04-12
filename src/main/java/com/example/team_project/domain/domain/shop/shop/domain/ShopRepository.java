package com.example.team_project.domain.domain.shop.shop.domain;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface ShopRepository extends JpaRepository<Shop,Long> {
    Shop findByShopName(String name);

    List<Shop> findBySellerId(Long seller);


}
