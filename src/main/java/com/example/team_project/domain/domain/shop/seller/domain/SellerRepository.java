package com.example.team_project.domain.domain.shop.seller.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller,Long> {




    Seller findByOwnerName(String name);

    Optional<Seller> findById(Long sellerId);


    //로그인체크
    Optional<Seller> findByOwnerId(String ownerId);
    boolean existsByOwnerId(String ownerId);

    default Seller validateSeller(String ownerId) {
        Optional<Seller> optionalSeller = findByOwnerId(ownerId);

        return optionalSeller.orElseThrow(() ->
                new UsernameNotFoundException("Invalid seller")
        );
    }



}
