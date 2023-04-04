package com.example.team_project.domain.domain.coupons.service.delete.coupon;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.domain.UserHaveCoupon;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExpiredCouponDeleterServiceImplTest {
    private final ExpiredCouponDeleterService expiredCouponDeleterService;
    private final UserHaveCouponRepository userHaveCouponRepository;
    private final CouponKindsRepository couponKindsRepository;
    private final UserRepository userRepository;

    @Autowired
    public ExpiredCouponDeleterServiceImplTest(ExpiredCouponDeleterService expiredCouponDeleterService, UserHaveCouponRepository userHaveCouponRepository, CouponKindsRepository couponKindsRepository, UserRepository userRepository) {
        this.expiredCouponDeleterService = expiredCouponDeleterService;
        this.userHaveCouponRepository = userHaveCouponRepository;
        this.couponKindsRepository = couponKindsRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 기간_만료_쿠폰_정상작동() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(user, couponKinds, LocalDate.now().plusDays(-1));
        userHaveCoupon.updateExpirationDate(LocalDate.now().plusDays(-5));
        userHaveCouponRepository.save(userHaveCoupon);

        expiredCouponDeleterService.delete();

        List<UserHaveCoupon> couponList = userHaveCouponRepository.findByUserId(user.getId());

        assertEquals(0, couponList.size());
    }

    @Test
    void 기간_만료_쿠폰_정상_만료쿠폰_없음() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        UserHaveCoupon userHaveCoupon1 = new UserHaveCoupon(user, couponKinds, LocalDate.now());
        userHaveCoupon1.updateExpirationDate(LocalDate.now().plusDays(5));
        userHaveCouponRepository.save(userHaveCoupon1);
        ;

        expiredCouponDeleterService.delete();

        List<UserHaveCoupon> couponList = userHaveCouponRepository.findByUserId(user.getId());

        assertEquals(1, couponList.size());
    }

    @Test
    void 기간_만료_쿠폰_정상_섞여있음() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        UserHaveCoupon userHaveCoupon1 = new UserHaveCoupon(user, couponKinds, LocalDate.now());
        userHaveCoupon1.updateExpirationDate(LocalDate.now().plusDays(-5));
        userHaveCouponRepository.save(userHaveCoupon1);

        UserHaveCoupon userHaveCoupon2 = new UserHaveCoupon(user, couponKinds, LocalDate.now());
        userHaveCoupon2.updateExpirationDate(LocalDate.now().plusDays(5));
        userHaveCouponRepository.save(userHaveCoupon2);

        expiredCouponDeleterService.delete();

        List<UserHaveCoupon> couponList = userHaveCouponRepository.findByUserId(user.getId());

        assertEquals(1, couponList.size());
        assertEquals(LocalDate.now().plusDays(5), couponList.get(0).getExpirationDate());
    }

    @Test
    void 기간_만료_쿠폰_정상_여러개() {
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        UserHaveCoupon userHaveCoupon1 = new UserHaveCoupon(user, couponKinds, LocalDate.now());
        userHaveCoupon1.updateExpirationDate(LocalDate.now().plusDays(-5));
        userHaveCouponRepository.save(userHaveCoupon1);

        UserHaveCoupon userHaveCoupon2 = new UserHaveCoupon(user, couponKinds, LocalDate.now());
        userHaveCoupon2.updateExpirationDate(LocalDate.now().plusDays(-3));
        userHaveCouponRepository.save(userHaveCoupon2);

        expiredCouponDeleterService.delete();

        List<UserHaveCoupon> couponList = userHaveCouponRepository.findByUserId(user.getId());

        assertEquals(0, couponList.size());
    }
}