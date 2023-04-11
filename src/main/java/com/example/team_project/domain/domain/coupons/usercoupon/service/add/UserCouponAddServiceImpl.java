package com.example.team_project.domain.domain.coupons.usercoupon.service.add;

import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.coupons.coupon.service.expiration.CouponExpirationCalculator;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.NotFoundCouponException;
import com.example.team_project.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCouponAddServiceImpl implements UserCouponAddService {
    private final CouponExpirationCalculator couponExpirationCalculator;
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Override
    public void add(Long userId, String couponName) {
        User user = getUser(userId);
        Coupon coupon = getCouponKinds(couponName);

        LocalDate couponExpirationDate = couponExpirationCalculator.setExpirationDate(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, couponExpirationDate);

        userCouponRepository.save(userCoupon);
    }


    private Coupon getCouponKinds(String couponName) {
        return couponRepository.findByName(couponName)
                .orElseThrow(() ->
                        new NotFoundCouponException("Coupon not found: " + couponName));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));
    }
}
