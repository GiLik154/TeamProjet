package com.example.team_project.domain.domain.shop.shop.domain;

import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD

public interface ShopRepository extends JpaRepository<Shop,Long> {
    Shop findByShopName(String name);
=======
import java.util.Optional;


public interface ShopRepository extends JpaRepository<Shop,Long> {
    Shop findByShopName(String name);


    Optional<Shop> findBySellerId(Long seller);
>>>>>>> e97cbad6628652ecf9dd4df84b65861ffe514662
}
