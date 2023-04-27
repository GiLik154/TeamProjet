package com.example.team_project.domain.domain.coupons.usercoupon.service.issue;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.CouponStatus;
import com.example.team_project.exception.NotFoundCouponException;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserCouponIssueServiceImplTest {
    private final UserCouponIssueService userCouponIssueService;
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;


    @Autowired
    UserCouponIssueServiceImplTest(UserCouponIssueService userCouponIssueService, UserCouponRepository userCouponRepository, CouponRepository couponRepository, UserRepository userRepository) {
        this.userCouponIssueService = userCouponIssueService;
        this.userCouponRepository = userCouponRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 쿠폰_발급_정상작동() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        userCouponIssueService.issue(userId, coupon.getName());

        List<UserCoupon> userCouponList = userCouponRepository.findByUserId(userId);
        UserCoupon userCoupon = userCouponList.get(0);

        assertEquals(user, userCoupon.getUser());
        assertEquals(coupon.getName(), userCoupon.getCoupon().getName());
        assertEquals(CouponStatus.UNUSED, userCoupon.getStatus());
    }

    @Test
    void 쿠폰_발급_최대_발급횟수_초과() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        userCouponIssueService.issue(userId, coupon.getName());

        List<UserCoupon> userCouponList = userCouponRepository.findByUserId(userId);

        assertNotEquals(2, userCouponList.size());
    }

    @Test
    void 쿠폰_발급_잘못된_쿠폰_이름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        NotFoundCouponException e = assertThrows(NotFoundCouponException.class, () ->
                userCouponIssueService.issue(userId, "wrongName"));
        assertTrue(e.getMessage().contains("Coupon not found: "));
    }

    @Test
    void 쿠폰_발급_유저_고유변호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);
        String couponName = coupon.getName();

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () ->
                userCouponIssueService.issue(userId + 1L, couponName));

        assertEquals("This user could not be found", e.getMessage());
    }
}