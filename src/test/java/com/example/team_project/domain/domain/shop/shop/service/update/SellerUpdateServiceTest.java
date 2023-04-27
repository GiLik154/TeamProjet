package com.example.team_project.domain.domain.shop.shop.service.update;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.update.SellerUpdateService;
import com.example.team_project.exception.NotPasswordException;
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
 class SellerUpdateServiceTest {

    private final SellerUpdateService sellerUpdateService;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SellerUpdateServiceTest(SellerUpdateService sellerUpdateService, SellerRepository sellerRepository, PasswordEncoder passwordEncoder) {
        this.sellerUpdateService = sellerUpdateService;
        this.sellerRepository = sellerRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Test
    void 수정_성공(){

        //판매자 회원가입
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);


        //판매자 아이디로 업데이트함 (판매자 이름, 판매자 폰번호)
        sellerUpdateService.sellerUpdate(seller.getId(),"testPw","testupdatename","testupdatephone");

        //오너 아이디 관련된거 가지고오기
        Seller sellerUpdate = sellerRepository.findByOwnerId(seller.getOwnerId()).get();


        //확인
        assertEquals("testId",sellerUpdate.getOwnerId());
        assertEquals("testupdatename",sellerUpdate.getOwnerName());
        assertEquals("testupdatephone",sellerUpdate.getPhoneNumber());



    }

    @Test
    void 수정_실패_비밀번호_다름(){

        //판매자 회원가입
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);

        //sellerId 가지고오기
        String sellerId = seller.getOwnerId();
       

        //비밀번호가 다를때 메시지 출력하는 코드
        NotPasswordException e = assertThrows(NotPasswordException.class, () ->
                sellerUpdateService.sellerUpdate(seller.getId(),"testPws","testupdatename","testupdatephone"));

        //오너아이디이용해서 검색 가지고오기
        Seller sellerUpdate = sellerRepository.findByOwnerId(seller.getOwnerId()).get();


        //확인
        assertEquals("testId",sellerUpdate.getOwnerId());
        assertNotEquals("testupdatename",seller.getOwnerName());
        assertNotEquals("testupdatephone",sellerUpdate.getPhoneNumber());



    }

}
