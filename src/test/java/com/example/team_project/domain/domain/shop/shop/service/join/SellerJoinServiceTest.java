package com.example.team_project.domain.domain.shop.shop.service.join;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.join.SellerJoinService;
import com.example.team_project.domain.domain.shop.seller.service.dto.SellerJoinDto;
import com.example.team_project.exception.SellerDuplicateSellerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
                "testPw",
                "testname",
                "testphonenumber");

        sellerJoinService.sellerJoin(sellerJoinDto);

        sellerRepository.findByOwnerId("testId").ifPresent(seller -> {
            assertEquals("testId",seller.getOwnerId());
            assertTrue(passwordEncoder.matches("testPw", seller.getPassword()));
            assertEquals("testname",seller.getOwnerName());
            assertEquals("testphonenumber",seller.getPhoneNumber());
                });
    }

    @Test
    void 판매자_등록_실패_아이디_중복(){
        SellerJoinDto sellerJoinDto = new SellerJoinDto(
                "testId",
                "testPw",
                "testname",
                "testphonenumber");

        sellerJoinService.sellerJoin(sellerJoinDto);

        SellerJoinDto sellerJoinDto2 = new SellerJoinDto(
                "testId",
                "testPw",
                "testname",
                "testphonenumber");
        

        assertThrows(SellerDuplicateSellerException.class, () -> sellerJoinService.sellerJoin(sellerJoinDto2));
        
    }


    @Test
    void 판매자_등록_실패_비밀번호_다름(){
        SellerJoinDto sellerJoinDto = new SellerJoinDto(
                "testId",
                "testPw",
                "testname",
                "testphonenumber");

        sellerJoinService.sellerJoin(sellerJoinDto);

        sellerRepository.findByOwnerId("testId").ifPresent(seller -> {
            assertEquals("testId",seller.getOwnerId());
            assertFalse(passwordEncoder.matches("testPws", seller.getPassword()));
            assertEquals("testname",seller.getOwnerName());
            assertEquals("testphonenumber",seller.getPhoneNumber());
        });



    }







}
    





