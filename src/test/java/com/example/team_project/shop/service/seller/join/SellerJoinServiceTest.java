package com.example.team_project.shop.service.seller.join;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.join.SellerJoinService;
import com.example.team_project.domain.domain.shop.seller.service.dto.SellerJoinDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SellerJoinServiceTest {

    private final SellerRepository sellerRepository;
    private final SellerJoinService sellerJoinService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    SellerJoinServiceTest(SellerJoinService sellerJoinService, SellerRepository sellerRepository, PasswordEncoder passwordEncoder){
        this.sellerJoinService = sellerJoinService;
        this.sellerRepository = sellerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    void 판매자_정상_등록(){
        SellerJoinDto sellerJoinDto = new SellerJoinDto(
                "testId",
                "testPassowrd",
                "testname",
                "testphonenumber");

        sellerJoinService.sellerJoin(sellerJoinDto);

        Seller seller = sellerRepository.findByOwnerName("testname");

        assertEquals("testId",seller.getOwnerId());
        assertNotEquals("testPassowrd",seller.getPassword());
        assertEquals("testname",seller.getOwnerName());
        assertEquals("testphonenumber",seller.getPhoneNumber());


    }




}
