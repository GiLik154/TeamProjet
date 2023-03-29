package com.example.team_project.shop.service.seller.delete;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.delete.SellerDeleteService;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 class SellerDeleteServiceTest {
    private final SellerRepository sellerRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;
    private final SellerDeleteService sellerDeleteService;


    @Autowired
    public SellerDeleteServiceTest(SellerRepository sellerRepository, ShopRepository shopRepository, PasswordEncoder passwordEncoder, SellerDeleteService sellerDeleteService) {
        this.sellerRepository = sellerRepository;
        this.shopRepository = shopRepository;
        this.sellerDeleteService = sellerDeleteService;
        this.passwordEncoder = passwordEncoder;
    }


    @Test
    void 삭제_성공() {
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        Long sellerId = seller.getId();
        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);

        //비밀번호 검증
        sellerDeleteService.delete(sellerId,seller.getOwnerId(),"testPw");

        //판매자 객체가 데이터베이스에 존재하지 않을 때 테스트 통과
        assertFalse(sellerRepository.findById(sellerId).isPresent());
    }

    @Test
    void 삭제_실패_아이디_다름() {
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        Long sellerId = seller.getId();

        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);

        //아이디가 다를때
        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                sellerDeleteService.delete(sellerId,"test123", "testPw")
        );

        assertTrue(sellerRepository.findById(sellerId).isPresent());
    }


    @Test
    void 삭제_실패_비밀번호_다름() {
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        Long sellerId = seller.getId();

        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);


        //비밀번호가 다를때
        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                sellerDeleteService.delete(sellerId,"testId", "s")
        );

        assertTrue(sellerRepository.findById(sellerId).isPresent());
    }

    @Test
    void 삭제_실패_아이디_비밀번호_다름() {
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        Long sellerId = seller.getId();

        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);


        //아이디, 비밀번호 둘다 다를때
        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                sellerDeleteService.delete(sellerId,"tessstId", "s")
        );

        assertTrue(sellerRepository.findById(sellerId).isPresent());
    }





}
