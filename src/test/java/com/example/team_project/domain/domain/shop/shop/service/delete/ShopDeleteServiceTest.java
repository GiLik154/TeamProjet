package com.example.team_project.domain.domain.shop.shop.service.delete;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.delete.SellerDeleteService;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.exception.NotPasswordException;
import com.example.team_project.exception.SellerNotFoundException;
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
class ShopDeleteServiceTest {
    private final SellerRepository sellerRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShopDeleteService shopDeleteService;


    @Autowired
    public ShopDeleteServiceTest(SellerRepository sellerRepository, ShopRepository shopRepository, PasswordEncoder passwordEncoder, ShopDeleteService shopDeleteService) {
        this.sellerRepository = sellerRepository;
        this.shopRepository = shopRepository;
        this.shopDeleteService = shopDeleteService;
        this.passwordEncoder = passwordEncoder;
    }





    @Test
    void 삭제_성공() {
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);

        String OwnerId = seller.getOwnerId();
        Long shopId = shop.getShopId();


        shopDeleteService.delete(shopId,OwnerId,"testPw");

        //seller 는 삭제되면안됨
        assertTrue(sellerRepository.findById(seller.getId()).isPresent());
        //shop 내용은 삭제
        assertFalse(shopRepository.findById(shopId).isPresent());
    }


    @Test
    void 삭제_실패_아이디_다름() {
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);

        String OwnerId = seller.getOwnerId();
        Long shopId = shop.getShopId();


        SellerNotFoundException e = assertThrows(SellerNotFoundException.class, () ->
                shopDeleteService.delete(shopId,"testids","testPw")
                     );


        //seller 는 삭제되면안됨
        assertTrue(sellerRepository.findById(seller.getId()).isPresent());
        //shop 내용은 삭제
        assertTrue(shopRepository.findById(shopId).isPresent());
    }

    @Test
    void 삭제_실패_비밀번호_다름() {
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);

        String ownerId = seller.getOwnerId();
        Long shopId = shop.getShopId();


        NotPasswordException e = assertThrows(NotPasswordException.class, () ->
                shopDeleteService.delete(shopId,ownerId,"testPws")
        );



        //seller 는 삭제되면안됨
        assertTrue(sellerRepository.findById(seller.getId()).isPresent());
        //shop 내용은 삭제
        assertTrue(shopRepository.findById(shopId).isPresent());
    }


    @Test
    void 삭제_실패_아이디_비밀번호_다름() {
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);

        Long shopId = shop.getShopId();


        SellerNotFoundException e = assertThrows(SellerNotFoundException.class, () ->
                shopDeleteService.delete(shopId,"testids","testPws")
        );


        //seller 는 삭제되면안됨
        assertTrue(sellerRepository.findById(seller.getId()).isPresent());
        //shop 내용은 삭제
        assertTrue(shopRepository.findById(shopId).isPresent());
    }




}