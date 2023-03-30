package com.example.team_project.domain.domain.coupons.service.add.coupon;

import com.example.team_project.domain.domain.coupons.domain.Coupon;
import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.domain.CouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.NotFoundCouponException;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponAddServiceImplTest {
    private final CouponAddService couponAddService;
    private final CouponRepository couponRepository;
    private final CouponKindsRepository couponKindsRepository;
    private final UserRepository userRepository;

    @Autowired
    CouponAddServiceImplTest(CouponAddService couponAddService, CouponRepository couponRepository, CouponKindsRepository couponKindsRepository, UserRepository userRepository) {
        this.couponAddService = couponAddService;
        this.couponRepository = couponRepository;
        this.couponKindsRepository = couponKindsRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 쿠폰_추가_정상작동() {
        User user = new User("testName", "testPw");
        userRepository.save(user);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        couponAddService.add(user.getId(), couponKinds.getName());

        Coupon coupon = couponRepository.findByCouponKindsName(couponKinds.getName()).get();

        assertNotNull(coupon.getId());
        assertEquals(user, coupon.getUser());
        assertEquals("testName", coupon.getCouponKinds().getName());
    }

    @Test
    void 쿠폰_추가_정상작동_유저_고유번호_없음() {
        User user = new User("testName", "testPw");
        userRepository.save(user);
        Long userId = user.getId();

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () ->
                couponAddService.add(userId + 1L, "testName")
        );

        assertEquals("This user could not be found", e.getMessage());
    }

    @Test
    void 쿠폰_추가_정상작동_쿠폰종류_이름없음() {
        User user = new User("testName", "testPw");
        userRepository.save(user);
        Long userId = user.getId();

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        NotFoundCouponException e = assertThrows(NotFoundCouponException.class, () ->
                couponAddService.add( userId, "wrongName")
        );

        assertEquals("Not Found Coupon", e.getMessage());
    }
}