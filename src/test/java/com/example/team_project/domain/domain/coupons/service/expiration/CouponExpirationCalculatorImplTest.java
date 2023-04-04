package com.example.team_project.domain.domain.coupons.service.expiration;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.domain.UserHaveCouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.ExpiredCouponException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponExpirationCalculatorImplTest {
    private final CouponExpirationCalculator couponExpirationCalculator;
    private final CouponKindsRepository couponKindsRepository;
    private final UserRepository userRepository;

    @Autowired
    CouponExpirationCalculatorImplTest(CouponExpirationCalculator couponExpirationCalculator, CouponKindsRepository couponKindsRepository, UserRepository userRepository) {
        this.couponExpirationCalculator = couponExpirationCalculator;
        this.couponKindsRepository = couponKindsRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 쿠폰_추가_정상작동_기간_설정() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKinds.updatePeriod(Period.ofDays(7));
        couponKindsRepository.save(couponKinds);

        LocalDate expirationDate = couponExpirationCalculator.setExpirationDate(couponKinds);

        assertEquals(LocalDate.now().plusDays(7), expirationDate);
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_설정_만료일이_더_김() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKinds.updateDeadline(LocalDate.now().plusDays(30));
        couponKinds.updatePeriod(Period.ofDays(7));
        couponKindsRepository.save(couponKinds);

        LocalDate expirationDate = couponExpirationCalculator.setExpirationDate(couponKinds);
        assertEquals(LocalDate.now().plusDays(30), expirationDate);
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_설정_기간이_더_김() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKinds.updateDeadline(LocalDate.now().plusDays(7));
        couponKinds.updatePeriod(Period.ofDays(30));
        couponKindsRepository.save(couponKinds);

        LocalDate expirationDate = couponExpirationCalculator.setExpirationDate(couponKinds);
        assertEquals(LocalDate.now().plusDays(7), expirationDate);
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_같음() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKinds.updateDeadline(LocalDate.now().plusDays(7));
        couponKinds.updatePeriod(Period.ofDays(7));
        couponKindsRepository.save(couponKinds);

        LocalDate expirationDate = couponExpirationCalculator.setExpirationDate(couponKinds);
        assertEquals(LocalDate.now().plusDays(7), expirationDate);
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_지났음() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKinds.updateDeadline(LocalDate.now().plusDays(-5));
        couponKindsRepository.save(couponKinds);

        ExpiredCouponException e = assertThrows(ExpiredCouponException.class, () ->
                couponExpirationCalculator.setExpirationDate(couponKinds)
        );

        assertEquals("Expired Coupon Exception", e.getMessage());
    }
}