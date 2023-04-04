package com.example.team_project.domain.domain.coupons.service.add.coupon;

import com.example.team_project.domain.domain.coupons.domain.UserHaveCoupon;
import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.domain.UserHaveCouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
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
class UserHaveCouponAddServiceImplTest {
    private final UserHaveCouponAddService couponAddService;
    private final UserHaveCouponRepository userHaveCouponRepository;
    private final CouponKindsRepository couponKindsRepository;
    private final UserRepository userRepository;

    @Autowired
    UserHaveCouponAddServiceImplTest(UserHaveCouponAddService couponAddService, UserHaveCouponRepository userHaveCouponRepository, CouponKindsRepository couponKindsRepository, UserRepository userRepository) {
        this.couponAddService = couponAddService;
        this.userHaveCouponRepository = userHaveCouponRepository;
        this.couponKindsRepository = couponKindsRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 쿠폰_추가_정상작동() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        couponAddService.add(user.getId(), couponKinds.getName());

        UserHaveCoupon userHaveCoupon = userHaveCouponRepository.findByCouponKindsName(couponKinds.getName()).get();

        assertNotNull(userHaveCoupon.getId());
        assertEquals(user, userHaveCoupon.getUser());
        assertEquals("testName", userHaveCoupon.getCouponKinds().getName());
    }

    @Test
    void 쿠폰_추가_정상작동_유저_고유번호_다름() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);
        Long userId = user.getId() + 1L;

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () ->
                couponAddService.add(userId, "testName")
        );

        assertEquals("User not found with id: " + userId, e.getMessage());
    }

    @Test
    void 쿠폰_추가_정상작동_쿠폰_고유번호_다름() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);
        Long userId = user.getId();

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        NotFoundCouponException e = assertThrows(NotFoundCouponException.class, () ->
                couponAddService.add(userId, "testName123")
        );

        assertEquals("Coupon kinds not found: testName123", e.getMessage());
    }

    @Test
    void 쿠폰_추가_정상작동_만료일설정() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKinds.updateDeadline(LocalDate.now());
        couponKindsRepository.save(couponKinds);

        couponAddService.add(user.getId(), couponKinds.getName());

        UserHaveCoupon userHaveCoupon = userHaveCouponRepository.findByCouponKindsName(couponKinds.getName()).get();

        assertNotNull(userHaveCoupon.getId());
        assertEquals(user, userHaveCoupon.getUser());
        assertEquals("testName", userHaveCoupon.getCouponKinds().getName());
        assertEquals(LocalDate.now(), userHaveCoupon.getExpirationDate());
    }

    @Test
    void 쿠폰_추가_정상작동_기간_설정() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKinds.updatePeriod(Period.ofDays(7));
        couponKindsRepository.save(couponKinds);

        couponAddService.add(user.getId(), couponKinds.getName());

        UserHaveCoupon userHaveCoupon = userHaveCouponRepository.findByCouponKindsName(couponKinds.getName()).get();

        assertNotNull(userHaveCoupon.getId());
        assertEquals(user, userHaveCoupon.getUser());
        assertEquals("testName", userHaveCoupon.getCouponKinds().getName());
        assertEquals(LocalDate.now().plusDays(7), userHaveCoupon.getExpirationDate());
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_설정_만료일이_더_김() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKinds.updateDeadline(LocalDate.now().plusDays(30));
        couponKinds.updatePeriod(Period.ofDays(7));
        couponKindsRepository.save(couponKinds);

        couponAddService.add(user.getId(), couponKinds.getName());

        UserHaveCoupon userHaveCoupon = userHaveCouponRepository.findByCouponKindsName(couponKinds.getName()).get();

        assertNotNull(userHaveCoupon.getId());
        assertEquals(user, userHaveCoupon.getUser());
        assertEquals("testName", userHaveCoupon.getCouponKinds().getName());
        assertEquals(LocalDate.now().plusDays(30), userHaveCoupon.getExpirationDate());
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_설정_기간이_더_김() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKinds.updateDeadline(LocalDate.now().plusDays(7));
        couponKinds.updatePeriod(Period.ofDays(30));
        couponKindsRepository.save(couponKinds);

        couponAddService.add(user.getId(), couponKinds.getName());

        UserHaveCoupon userHaveCoupon = userHaveCouponRepository.findByCouponKindsName(couponKinds.getName()).get();

        assertNotNull(userHaveCoupon.getId());
        assertEquals(user, userHaveCoupon.getUser());
        assertEquals("testName", userHaveCoupon.getCouponKinds().getName());
        assertEquals(LocalDate.now().plusDays(7), userHaveCoupon.getExpirationDate());
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_기간_같음() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKinds.updateDeadline(LocalDate.now().plusDays(7));
        couponKinds.updatePeriod(Period.ofDays(7));
        couponKindsRepository.save(couponKinds);

        couponAddService.add(user.getId(), couponKinds.getName());

        UserHaveCoupon userHaveCoupon = userHaveCouponRepository.findByCouponKindsName(couponKinds.getName()).get();

        assertNotNull(userHaveCoupon.getId());
        assertEquals(user, userHaveCoupon.getUser());
        assertEquals("testName", userHaveCoupon.getCouponKinds().getName());
        assertEquals(LocalDate.now().plusDays(7), userHaveCoupon.getExpirationDate());
    }

    @Test
    void 쿠폰_추가_정상작동_만료일_지났음() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);
        Long userId = user.getId();

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKinds.updateDeadline(LocalDate.now().plusDays(-5));
        couponKindsRepository.save(couponKinds);

        ExpiredCouponException e = assertThrows(ExpiredCouponException.class, () ->
                couponAddService.add(userId, "testName")
        );

        assertEquals("Expired Coupon Exception", e.getMessage());
    }
}