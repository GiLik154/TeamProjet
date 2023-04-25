package com.example.team_project.domain.domain.coupons.usercoupon;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaTest {
    private final UserCouponRepository userCouponRepository;

    @Autowired
    public JpaTest(UserCouponRepository userCouponRepository) {
        this.userCouponRepository = userCouponRepository;
    }

    @Test
    void name() {
        UserCoupon userCoupon = userCouponRepository.findDistinctWithCouponById(46L).get();

        Coupon coupon = userCoupon.getCoupon();

        System.out.println(coupon.getName());
    }
}
