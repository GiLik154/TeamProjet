package com.example.team_project.domain.domain.shop.seller.domain;

import com.example.team_project.exception.SellerDuplicateSellerException;
import com.example.team_project.exception.SellerNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findByOwnerName(String name);

    Optional<Seller> findById(Long sellerId);

    //아이디찾기
    Optional<Seller> findByOwnerNameAndPhoneNumber(String name, String phoneNumber);

    //로그인체크
    Optional<Seller> findByOwnerId(String ownerId);
    //전화번호체크
    Optional<Seller> findByPhoneNumber(String phoneNumber);


    default Seller validateSeller(String ownerId) {
        Optional<Seller> optionalSeller = findByOwnerId(ownerId);

        return optionalSeller.orElseThrow(() ->
                new SellerNotFoundException("아이디가 존재 하지않습니다.")
        );

    }

    //아이디있나확인
    default void duplication(String ownerId,String phoneNumber) {
        Optional<Seller> optionalSeller = findByOwnerId(ownerId);
        Optional<Seller> optionalPhoneNumber = findByPhoneNumber(phoneNumber);

        if (optionalSeller.isPresent()) {
            throw new SellerDuplicateSellerException("이미 존재하는 아이디입니다.");

        }

        if (optionalPhoneNumber.isPresent()) {
            throw new SellerDuplicateSellerException("이미 존재하는 전화번호입니다.");

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
