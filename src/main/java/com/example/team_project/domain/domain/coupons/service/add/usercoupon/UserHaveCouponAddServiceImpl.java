package com.example.team_project.domain.domain.coupons.service.add.usercoupon;

import com.example.team_project.domain.domain.coupons.domain.UserHaveCoupon;
import com.example.team_project.domain.domain.coupons.domain.Coupon;
import com.example.team_project.domain.domain.coupons.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.domain.UserHaveCouponRepository;
import com.example.team_project.domain.domain.coupons.service.expiration.CouponExpirationCalculator;
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
public class UserHaveCouponAddServiceImpl implements UserHaveCouponAddService {
    private final CouponExpirationCalculator couponExpirationCalculator;
    private final UserHaveCouponRepository userHaveCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Override
    public void add(Long userId, String couponKindsName) {
        User user = getUser(userId);
        Coupon coupon = getCouponKinds(couponKindsName);

        LocalDate couponExpirationDate = couponExpirationCalculator.setExpirationDate(coupon);

        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(user, coupon, couponExpirationDate);

        userHaveCouponRepository.save(userHaveCoupon);
    }


    private Coupon getCouponKinds(String couponKindsName) {
        return couponRepository.findByName(couponKindsName)
                .orElseThrow(() ->
                        new NotFoundCouponException("Coupon kinds not found: " + couponKindsName));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));
    }
}
