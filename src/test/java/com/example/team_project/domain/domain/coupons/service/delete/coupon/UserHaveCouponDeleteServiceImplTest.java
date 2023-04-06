package com.example.team_project.domain.domain.coupons.service.delete.coupon;

import com.example.team_project.domain.domain.coupons.domain.UserHaveCoupon;
import com.example.team_project.domain.domain.coupons.domain.Coupon;
import com.example.team_project.domain.domain.coupons.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.domain.UserHaveCouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserHaveCouponDeleteServiceImplTest {
    private final UserHaveCouponDeleteService couponDeleteService;
    private final UserHaveCouponRepository userHaveCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Autowired
    UserHaveCouponDeleteServiceImplTest(UserHaveCouponDeleteService couponDeleteService, UserHaveCouponRepository userHaveCouponRepository, CouponRepository couponRepository, UserRepository userRepository) {
        this.couponDeleteService = couponDeleteService;
        this.userHaveCouponRepository = userHaveCouponRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 쿠폰_삭제_정상작동() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 5, 10000);
        couponRepository.save(coupon);

        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(user, coupon, LocalDate.now());
        userHaveCouponRepository.save(userHaveCoupon);

        couponDeleteService.delete(user.getId(), userHaveCoupon.getId());

        assertFalse(userHaveCouponRepository.findById(userHaveCoupon.getId()).isPresent());
    }

    @Test
    void 쿠폰_삭제_유저_고유변호_다름() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 5, 10000);
        couponRepository.save(coupon);

        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(user, coupon, LocalDate.now());
        userHaveCouponRepository.save(userHaveCoupon);
        Long couponId = userHaveCoupon.getId();

        couponDeleteService.delete(userId + 1L, couponId);

        assertTrue(userHaveCouponRepository.findById(userHaveCoupon.getId()).isPresent());
    }

    @Test
    void 쿠폰_삭제_쿠폰_고유변호_다름() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 5, 10000);
        couponRepository.save(coupon);

        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(user, coupon, LocalDate.now());
        userHaveCouponRepository.save(userHaveCoupon);
        Long couponId = userHaveCoupon.getId();

        couponDeleteService.delete(userId, couponId + 1L);

        assertTrue(userHaveCouponRepository.findById(userHaveCoupon.getId()).isPresent());
    }
}