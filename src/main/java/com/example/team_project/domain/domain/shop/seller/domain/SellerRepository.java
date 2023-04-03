package com.example.team_project.domain.domain.shop.seller.domain;

import com.example.team_project.exception.SellerDuplicateSellerException;
import com.example.team_project.exception.SellerNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller,Long> {

    Seller findByOwnerName(String name);

    Optional<Seller> findById(Long sellerId);


    //로그인체크
    Optional<Seller> findByOwnerId(String ownerId);


    default Seller validateSeller(String ownerId) {
        Optional<Seller> optionalSeller = findByOwnerId(ownerId);

        return optionalSeller.orElseThrow(() ->
                new UsernameNotFoundException("Invalid seller")
        );

    }

    default void duplication(String ownerId){
        Optional<Seller> optionalSeller = findByOwnerId(ownerId);

        if(optionalSeller.isPresent()){
            throw new SellerDuplicateSellerException();

        }


    }


    default Seller validateSellerId(Long sellerId) {
        Optional<Seller> optionalSeller = findById(sellerId);

        return optionalSeller.orElseThrow(() ->
                new UsernameNotFoundException("Invalid seller")
        );

    }

    void deleteByOwnerId(String ownerId);





}
