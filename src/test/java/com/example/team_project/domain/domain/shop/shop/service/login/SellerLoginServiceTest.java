package com.example.team_project.domain.domain.shop.shop.service.login;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.login.SellerLoginService;
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
 class SellerLoginServiceTest {

    private final SellerRepository sellerRepository;
    private final SellerLoginService sellerLoginService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SellerLoginServiceTest(SellerRepository sellerRepository, SellerLoginService sellerLoginService, PasswordEncoder passwordEncoder) {
        this.sellerRepository = sellerRepository;
        this.sellerLoginService = sellerLoginService;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    void 로그인_성공() {
       //판매자 회원가입
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);

        // 로그인 시도
        String SellerId = seller.getOwnerId();
        String result = sellerLoginService.sellerLogin("testId", "testPw");

        // 판매자 ID가 반환되었는지 확인
        assertEquals(SellerId, result);
    }

    @Test
    void 로그인_아이디가_다름() {
        // 판매자 회원가입
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);

        // 존재하지 않는 판매자 ID로 로그인 시도
        SellerNotFoundException e = assertThrows(SellerNotFoundException.class, () ->
                sellerLoginService.sellerLogin("test123", "test")
        );

        assertEquals("아이디가 존재 하지않습니다.", e.getMessage());
    }

    @Test
    void 로그인_패스워드가_다름(){
        // 판매자 회원가입
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);

        // 존재하지 않는 판매자 Password 로그인 시도
        NotPasswordException e = assertThrows(NotPasswordException.class, () ->
                sellerLoginService.sellerLogin("testId", "test123")
        );

        assertEquals("비밀번호를 다시 확인해주세요", e.getMessage());

    }

    @Test
    void 로그인_아이디_비밀번호_다름(){
        // 판매자 회원가입
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);

        // 존재하지 않는 판매자 Id,Password 로그인 시도
        SellerNotFoundException e = assertThrows(SellerNotFoundException.class, () ->
                sellerLoginService.sellerLogin("testsId", "test123")
        );

        assertEquals("아이디가 존재 하지않습니다.", e.getMessage());

    }


}
