package com.example.team_project.domain.domain.coupons.coupon.service.update;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.coupons.coupon.service.update.ExpiredCouponUpdateService;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.CouponStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExpiredCouponUpdateServiceImplTest {
    private final ExpiredCouponUpdateService expiredCouponUpdateService;
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Autowired
    public ExpiredCouponUpdateServiceImplTest(ExpiredCouponUpdateService expiredCouponUpdateService, UserCouponRepository userCouponRepository, CouponRepository couponRepository, UserRepository userRepository) {
        this.expiredCouponUpdateService = expiredCouponUpdateService;
        this.userCouponRepository = userCouponRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 기간_만료_쿠폰_정상작동() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now().plusDays(-1));
        userCoupon.updateExpirationDate(LocalDate.now().plusDays(-5));
        userCouponRepository.save(userCoupon);

        expiredCouponUpdateService.delete();

        assertEquals(CouponStatus.EXPIRED, userCoupon.getStatus());
    }

    @Test
    void 기간_만료_쿠폰_정상_만료쿠폰_없음() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCoupon.updateExpirationDate(LocalDate.now().plusDays(5));
        userCouponRepository.save(userCoupon);
        ;

        expiredCouponUpdateService.delete();

        assertEquals(CouponStatus.UNUSED, userCoupon.getStatus());
    }

    @Test
    void 기간_만료_쿠폰_정상_섞여있음() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon1 = new UserCoupon(user, coupon, LocalDate.now());
        userCoupon1.updateExpirationDate(LocalDate.now().plusDays(-5));
        userCouponRepository.save(userCoupon1);

        UserCoupon userCoupon2 = new UserCoupon(user, coupon, LocalDate.now());
        userCoupon2.updateExpirationDate(LocalDate.now().plusDays(5));
        userCouponRepository.save(userCoupon2);

        expiredCouponUpdateService.delete();

        assertEquals(CouponStatus.EXPIRED, userCoupon1.getStatus());
        assertEquals(CouponStatus.UNUSED, userCoupon2.getStatus());
    }

    @Test
    void 기간_만료_쿠폰_정상_여러개() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon1 = new UserCoupon(user, coupon, LocalDate.now());
        userCoupon1.updateExpirationDate(LocalDate.now().plusDays(-5));
        userCouponRepository.save(userCoupon1);

        UserCoupon userCoupon2 = new UserCoupon(user, coupon, LocalDate.now());
        userCoupon2.updateExpirationDate(LocalDate.now().plusDays(-3));
        userCouponRepository.save(userCoupon2);

        expiredCouponUpdateService.delete();

        assertEquals(CouponStatus.EXPIRED, userCoupon1.getStatus());
        assertEquals(CouponStatus.EXPIRED, userCoupon2.getStatus());
    }
}