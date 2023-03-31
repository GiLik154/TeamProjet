package com.example.team_project.shop.service;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.SellerJoinService;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.domain.domain.shop.shop.service.ShopJoinService;
import com.example.team_project.domain.domain.shop.shop.service.dto.ShopJoinDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 class ShopJoinServiceTest {
    private final SellerRepository sellerRepository;
    private final SellerJoinService sellerJoinService;
    private final ShopRepository shopRepository;
    private final ShopJoinService shopJoinService;


    @Autowired
    ShopJoinServiceTest(SellerJoinService sellerJoinService,
                        SellerRepository sellerRepository,
                        ShopRepository shopRepository,
                        ShopJoinService shopJoinService
    ) {
        this.sellerJoinService = sellerJoinService;
        this.sellerRepository = sellerRepository;
        this.shopJoinService = shopJoinService;
        this.shopRepository = shopRepository;
    }

    @Test
    void 판매자_정상_등록() {
        Seller seller = new Seller("testId", "testPw", "testName", "testPhone");
        sellerRepository.save(seller);

        //가게등록을 따로함
        ShopJoinDto shopJoinDto = new ShopJoinDto(
                "testshopname",
                "testshopaddress",
                "testregnumber"
        );

        shopJoinService.shopJoin(seller.getId(), shopJoinDto);

        Shop shop = shopRepository.findByShopName("testshopname");

        assertEquals(seller, shop.getSeller());
        assertEquals("testshopname", shop.getShopName());
        assertEquals("testshopaddress", shop.getShopAddress());
        assertEquals("testregnumber", shop.getBusinessRegistrationNumber());
    }


}
