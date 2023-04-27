package com.example.team_project.domain.domain.coupons.usercoupon.service.issue;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserIssueAbleCouponListServiceImplTest {
    private final UserIssueAbleCouponListService userIssueAbleCouponListService;
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;


    @Autowired
    UserIssueAbleCouponListServiceImplTest(UserIssueAbleCouponListService userIssueAbleCouponListService, UserCouponRepository userCouponRepository, CouponRepository couponRepository, UserRepository userRepository) {
        this.userIssueAbleCouponListService = userIssueAbleCouponListService;
        this.userCouponRepository = userCouponRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 쿠폰_발급_가능_리스트_출력_정상작동() {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon);

        List<Coupon> list = userIssueAbleCouponListService.getList(userId);

        Coupon testCoupon = list.get(list.size() - 1);

        assertEquals(coupon.getName(), testCoupon.getName());
        assertEquals(coupon.getDiscountRate(), testCoupon.getDiscountRate());
        assertEquals(coupon.getMaxCouponCount(), testCoupon.getMaxCouponCount());
    }

    @Test
    void 쿠폰_발급_가능_리스트_출력_정상작동_여러개() {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon1 = new Coupon("testName1", 50, 10000, 1);
        coupon1.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon1);

        Coupon coupon2 = new Coupon("testName2", 50, 10000, 1);
        coupon2.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon2);

        Coupon coupon3 = new Coupon("testName3", 50, 10000, 1);
        coupon3.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon3);

        List<Coupon> list = userIssueAbleCouponListService.getList(userId);

        assertTrue(3 <= list.size());
    }

    @Test
    void 쿠폰_발급_가능_리스트_출력_정상작동_여러개_섞임() {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon1 = new Coupon("testName1", 50, 10000, 1);
        coupon1.updateDeadline(LocalDate.now().plusDays(-5));
        couponRepository.save(coupon1);

        Coupon coupon2 = new Coupon("testName2", 50, 10000, 1);
        coupon2.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon2);

        Coupon coupon3 = new Coupon("testName3", 50, 10000, 1);
        coupon3.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon3);

        UserCoupon userCoupon = new UserCoupon(user, coupon3, LocalDate.now());
        userCouponRepository.save(userCoupon);

        List<Coupon> list = userIssueAbleCouponListService.getList(userId);

        Coupon testCoupon = list.get(list.size() - 1);

        assertFalse(list.contains(coupon1));
        assertFalse(list.contains(coupon3));
        assertEquals(coupon2.getName(), testCoupon.getName());
    }

    @Test
    void 쿠폰_발급_가능_리스트_출력_유저_아이디_다름() {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon);

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () ->
                userIssueAbleCouponListService.getList(userId + 1L));

        assertEquals("This user could not be found", e.getMessage());
    }

    @Test
    void 쿠폰_발급_가능_리스트_출력_쿠폰_만료() {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(-7));
        couponRepository.save(coupon);

        List<Coupon> list = userIssueAbleCouponListService.getList(userId);

        assertFalse(list.contains(coupon));
    }

    @Test
    void 쿠폰_발급_가능_리스트_출력_발급_수량_초과() {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        List<Coupon> list = userIssueAbleCouponListService.getList(userId);

        assertFalse(list.contains(coupon));
    }
}