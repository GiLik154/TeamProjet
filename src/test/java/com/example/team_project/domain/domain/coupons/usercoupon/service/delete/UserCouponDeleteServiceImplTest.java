package com.example.team_project.domain.domain.coupons.usercoupon.service.delete;

import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.service.delete.UserCouponDeleteService;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
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
class UserCouponDeleteServiceImplTest {
    private final UserCouponDeleteService couponDeleteService;
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Autowired
    UserCouponDeleteServiceImplTest(UserCouponDeleteService couponDeleteService, UserCouponRepository userCouponRepository, CouponRepository couponRepository, UserRepository userRepository) {
        this.couponDeleteService = couponDeleteService;
        this.userCouponRepository = userCouponRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 쿠폰_삭제_정상작동() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        couponDeleteService.delete(user.getId(), userCoupon.getId());

        assertFalse(userCouponRepository.findById(userCoupon.getId()).isPresent());
    }

    @Test
    void 쿠폰_삭제_유저_고유변호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);
        Long couponId = userCoupon.getId();

        couponDeleteService.delete(userId + 1L, couponId);

        assertTrue(userCouponRepository.findById(userCoupon.getId()).isPresent());
    }

    @Test
    void 쿠폰_삭제_쿠폰_고유변호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);
        Long couponId = userCoupon.getId();

        couponDeleteService.delete(userId, couponId + 1L);

        assertTrue(userCouponRepository.findById(userCoupon.getId()).isPresent());
    }
}