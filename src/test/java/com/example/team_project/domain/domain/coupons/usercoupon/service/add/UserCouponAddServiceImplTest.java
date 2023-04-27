package com.example.team_project.domain.domain.coupons.usercoupon.service.add;

import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.service.add.UserCouponAddService;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.ExpiredCouponException;
import com.example.team_project.exception.NotFoundCouponException;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserCouponAddServiceImplTest {
    private final UserCouponAddService couponAddService;
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Autowired
    UserCouponAddServiceImplTest(UserCouponAddService couponAddService, UserCouponRepository userCouponRepository, CouponRepository couponRepository, UserRepository userRepository) {
        this.couponAddService = couponAddService;
        this.userCouponRepository = userCouponRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 쿠폰_추가_정상작동() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        System.out.println("============================================================");

        couponAddService.add(user.getId(), coupon.getName());

        System.out.println("============================================================");

        UserCoupon userCoupon = userCouponRepository.findByCouponName(coupon.getName()).get();

        assertNotNull(userCoupon.getId());
        assertEquals(user, userCoupon.getUser());
        assertEquals("testName", userCoupon.getCoupon().getName());
    }

    @Test
    void 쿠폰_추가_정상작동_유저_고유번호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId() + 1L;

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () ->
                couponAddService.add(userId, "testName")
        );

        assertEquals("User not found with id: " + userId, e.getMessage());
    }

    @Test
    void 쿠폰_추가_정상작동_쿠폰_고유번호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        NotFoundCouponException e = assertThrows(NotFoundCouponException.class, () ->
                couponAddService.add(userId, "testName123")
        );

        assertTrue(e.getMessage().contains("Coupon not found: "));
    }

    @Test
    void 쿠폰_추가_정상작동_만료일설정() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now());
        couponRepository.save(coupon);

        couponAddService.add(user.getId(), coupon.getName());

        UserCoupon userCoupon = userCouponRepository.findByCouponName(coupon.getName()).get();

        assertNotNull(userCoupon.getId());
        assertEquals(user, userCoupon.getUser());
        assertEquals("testName", userCoupon.getCoupon().getName());
        assertEquals(LocalDate.now(), userCoupon.getExpirationDate());
    }

    @Test
    void 쿠폰_추가_정상작동_기간_설정() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updatePeriod(Period.ofDays(7));
        couponRepository.save(coupon);

        couponAddService.add(user.getId(), coupon.getName());

        UserCoupon userCoupon = userCouponRepository.findByCouponName(coupon.getName()).get();

        assertNotNull(userCoupon.getId());
        assertEquals(user, userCoupon.getUser());
        assertEquals("testName", userCoupon.getCoupon().getName());
        assertEquals(LocalDate.now().plusDays(7), userCoupon.getExpirationDate());
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_설정_만료일이_더_김() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(30));
        coupon.updatePeriod(Period.ofDays(7));
        couponRepository.save(coupon);

        couponAddService.add(user.getId(), coupon.getName());

        UserCoupon userCoupon = userCouponRepository.findByCouponName(coupon.getName()).get();

        assertNotNull(userCoupon.getId());
        assertEquals(user, userCoupon.getUser());
        assertEquals("testName", userCoupon.getCoupon().getName());
        assertEquals(LocalDate.now().plusDays(30), userCoupon.getExpirationDate());
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_설정_기간이_더_김() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        coupon.updatePeriod(Period.ofDays(30));
        couponRepository.save(coupon);

        couponAddService.add(user.getId(), coupon.getName());

        UserCoupon userCoupon = userCouponRepository.findByCouponName(coupon.getName()).get();

        assertNotNull(userCoupon.getId());
        assertEquals(user, userCoupon.getUser());
        assertEquals("testName", userCoupon.getCoupon().getName());
        assertEquals(LocalDate.now().plusDays(7), userCoupon.getExpirationDate());
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_같음() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        coupon.updatePeriod(Period.ofDays(7));
        couponRepository.save(coupon);

        couponAddService.add(user.getId(), coupon.getName());

        UserCoupon userCoupon = userCouponRepository.findByCouponName(coupon.getName()).get();

        assertNotNull(userCoupon.getId());
        assertEquals(user, userCoupon.getUser());
        assertEquals("testName", userCoupon.getCoupon().getName());
        assertEquals(LocalDate.now().plusDays(7), userCoupon.getExpirationDate());
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_지났음() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(-5));
        couponRepository.save(coupon);

        ExpiredCouponException e = assertThrows(ExpiredCouponException.class, () ->
                couponAddService.add(userId, "testName")
        );

        assertEquals("Expired Coupon Exception", e.getMessage());
    }
}