package com.example.team_project.domain.domain.shop.shop.service.update;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.domain.domain.shop.shop.service.update.ShopUpdateService;
import com.example.team_project.exception.NotPasswordException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 class ShopUpdateServiceTest {

    private final SellerRepository sellerRepository;
    private final ShopRepository shopRepository;
    private final ShopUpdateService shopUpdateService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ShopUpdateServiceTest(SellerRepository sellerRepository, ShopRepository shopRepository, ShopUpdateService shopUpdateService, PasswordEncoder passwordEncoder) {
        this.sellerRepository = sellerRepository;
        this.shopRepository = shopRepository;
        this.shopUpdateService = shopUpdateService;
        this.passwordEncoder = passwordEncoder;
    }


    //판매처 이름,판매처 주소 수정
    @Test
    void 수정_성공() {
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);

        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);

        Long sellerId = seller.getId();
        Long shopId = shop.getShopId();

        shopUpdateService.shopUpdate(sellerId, shopId, "testPw", "testupdateshopName", "testupdateshopaddress");

        Shop sellerUpdate = shopRepository.findById(shopId).get();

        //확인
        assertEquals("testupdateshopName", sellerUpdate.getShopName());
        assertEquals("testupdateshopaddress", sellerUpdate.getShopAddress());


    }

    @Test
    void 수정_실패_비밀번호_틀림() {
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);

        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);

        Long sellerId = seller.getId();
        Long shopId = shop.getShopId();

        NotPasswordException e = assertThrows(NotPasswordException.class, () ->
                shopUpdateService.shopUpdate(sellerId, shopId, "testPsw", "testupdateshopName", "testupdateshopaddress"));

        Shop sellerUpdate = shopRepository.findById(shopId).get();

        //확인
        assertNotEquals("testupdateshopName", sellerUpdate.getShopName());
        assertNotEquals("testupdateshopaddress", sellerUpdate.getShopAddress());


    }

}
