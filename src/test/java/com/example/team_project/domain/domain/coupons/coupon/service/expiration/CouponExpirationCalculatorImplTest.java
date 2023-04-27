package com.example.team_project.domain.domain.coupons.coupon.service.expiration;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.coupon.service.expiration.CouponExpirationCalculator;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
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
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Autowired
    CouponExpirationCalculatorImplTest(CouponExpirationCalculator couponExpirationCalculator, CouponRepository couponRepository, UserRepository userRepository) {
        this.couponExpirationCalculator = couponExpirationCalculator;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 쿠폰_추가_정상작동_기간_설정() {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updatePeriod(Period.ofDays(7));
        couponRepository.save(coupon);

        LocalDate expirationDate = couponExpirationCalculator.setExpirationDate(coupon);

        assertEquals(LocalDate.now().plusDays(7), expirationDate);
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_설정_만료일이_더_김() {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(30));
        coupon.updatePeriod(Period.ofDays(7));
        couponRepository.save(coupon);

        LocalDate expirationDate = couponExpirationCalculator.setExpirationDate(coupon);
        assertEquals(LocalDate.now().plusDays(30), expirationDate);
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_설정_기간이_더_김() {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        coupon.updatePeriod(Period.ofDays(30));
        couponRepository.save(coupon);

        LocalDate expirationDate = couponExpirationCalculator.setExpirationDate(coupon);
        assertEquals(LocalDate.now().plusDays(7), expirationDate);
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_같음() {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        coupon.updatePeriod(Period.ofDays(7));
        couponRepository.save(coupon);

        LocalDate expirationDate = couponExpirationCalculator.setExpirationDate(coupon);
        assertEquals(LocalDate.now().plusDays(7), expirationDate);
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_지났음() {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(-5));
        couponRepository.save(coupon);

        ExpiredCouponException e = assertThrows(ExpiredCouponException.class, () ->
                couponExpirationCalculator.setExpirationDate(coupon)
        );

        assertEquals("Expired Coupon Exception", e.getMessage());
    }
}