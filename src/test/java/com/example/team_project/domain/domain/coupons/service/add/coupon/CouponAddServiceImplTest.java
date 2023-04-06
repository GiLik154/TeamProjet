package com.example.team_project.domain.domain.coupons.service.add.coupon;

import com.example.team_project.domain.domain.coupons.domain.Coupon;
import com.example.team_project.domain.domain.coupons.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.service.add.coupon.dto.CouponAddServiceDto;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.InvalidCouponInfo;
import com.example.team_project.exception.UserNotCreativeCouponException;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponAddServiceImplTest {
    private final CouponAddService couponAddService;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    @Autowired
    CouponAddServiceImplTest(CouponAddService couponAddService, UserRepository userRepository, CouponRepository couponRepository) {
        this.couponAddService = couponAddService;
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
    }

    @Test
    void 쿠폰_종류_생성_정상작동_할인율1() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 1, 10000, null, null);
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(1, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_할인율99() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 99, 10000, null, null);
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(99, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_최소구매1() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 50, 1, null, null);
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(50, coupon.getDiscountRate());
        assertEquals(1, coupon.getMinPrice());
    }

    @Test
    void 쿠폰_종류_생성_할인율_0이하() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 0, 100, null, null);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponAddService.add(userId, dto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_데드라인_설정() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 1, 10000, LocalDate.now(), null);
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(1, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
        assertEquals(LocalDate.now(), coupon.getDeadline());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_기간_설정() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 1, 10000, null, Period.ofDays(7));
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(1, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
        assertEquals(Period.ofDays(7), coupon.getPeriod());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_기간And데드라인_설정() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 1, 10000, LocalDate.now(), Period.ofDays(7));
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(1, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
        assertEquals(LocalDate.now(), coupon.getDeadline());
        assertEquals(Period.ofDays(7), coupon.getPeriod());
    }

    @Test
    void 쿠폰_종류_생성_할인율_100이상() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 100, 100, null, null);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponAddService.add(userId, dto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰_종류_생성_최소구매_0미만() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 50, 0, null, null);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponAddService.add(userId, dto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰_종류_생성_유저_고유키_다름() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 50, 0, null, null);

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () ->
                couponAddService.add(userId + 1L, dto));

        assertEquals("This user could not be found", e.getMessage());
    }

    @Test
    void 쿠폰_종류_생성_유저_등급_다름() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.GOLD);
        userRepository.save(user);
        Long userId = user.getId();

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 50, 0, null, null);

        UserNotCreativeCouponException e = assertThrows(UserNotCreativeCouponException.class, () ->
                couponAddService.add(userId, dto));

        assertEquals("The user tried to force a coupon user ID :" + userId, e.getMessage());
    }
}