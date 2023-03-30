package com.example.team_project.domain.domain.coupons.service.delete.coupon;

import com.example.team_project.domain.domain.coupons.domain.Coupon;
import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.domain.CouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponDeleteServiceImplTest {
    private final CouponDeleteService couponDeleteService;
    private final CouponRepository couponRepository;
    private final CouponKindsRepository couponKindsRepository;
    private final UserRepository userRepository;

    @Autowired
    CouponDeleteServiceImplTest(CouponDeleteService couponDeleteService, CouponRepository couponRepository, CouponKindsRepository couponKindsRepository, UserRepository userRepository) {
        this.couponDeleteService = couponDeleteService;
        this.couponRepository = couponRepository;
        this.couponKindsRepository = couponKindsRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 쿠폰_삭제_정상작동() {
        User user = new User("testName", "testPw");
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        Coupon coupon = new Coupon(user, couponKinds);
        couponRepository.save(coupon);

        couponDeleteService.delete(user.getId(), coupon.getId());

        assertFalse(couponRepository.findById(coupon.getId()).isPresent());
    }

    @Test
    void 쿠폰_삭제_유저_고유변호_다름() {
        User user = new User("testName", "testPw");
        userRepository.save(user);
        Long userId = user.getId();

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        Coupon coupon = new Coupon(user, couponKinds);
        couponRepository.save(coupon);
        Long couponId = coupon.getId();

        couponDeleteService.delete(userId + 1L, couponId);

        assertTrue(couponRepository.findById(coupon.getId()).isPresent());
    }

    @Test
    void 쿠폰_삭제_쿠폰_고유변호_다름() {
        User user = new User("testName", "testPw");
        userRepository.save(user);
        Long userId = user.getId();

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        Coupon coupon = new Coupon(user, couponKinds);
        couponRepository.save(coupon);
        Long couponId = coupon.getId();

        couponDeleteService.delete(userId, couponId + 1L);

        assertTrue(couponRepository.findById(coupon.getId()).isPresent());
    }
}