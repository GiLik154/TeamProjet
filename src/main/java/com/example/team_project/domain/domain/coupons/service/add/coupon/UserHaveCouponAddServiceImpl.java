package com.example.team_project.domain.domain.coupons.service.add.coupon;

import com.example.team_project.domain.domain.coupons.domain.UserHaveCoupon;
import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
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
    private final CouponKindsRepository couponKindsRepository;
    private final UserRepository userRepository;

    /**
     * 컨트롤단에서 유저 아이디, 쿠폰 종류의 이름을 받아와서 추가해야함.
     */
    @Override
    public void add(Long userId, String couponKindsName) {
        CouponKinds couponKinds = getCouponKinds(couponKindsName);
        User user = getUser(userId);

        LocalDate couponExpirationDate = couponExpirationCalculator.setExpirationDate(couponKinds);

        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(user, couponKinds, couponExpirationDate);

        userHaveCouponRepository.save(userHaveCoupon);
    }

    /**
     * CouponKinds를 찾는다.
     * 없으면 NotFoundCouponException 익셉선 발생
     * CouponKinds의 고유키값을 넣어서 디버깅 하기 유용하게 만듬.
     */
    private CouponKinds getCouponKinds(String couponKindsName) {
        return couponKindsRepository.findByName(couponKindsName)
                .orElseThrow(() ->
                        new NotFoundCouponException("Coupon kinds not found: " + couponKindsName));
    }

    /**
     * User를 찾는다.
     * 없으면 UserNotFoundException 익셉선 발생
     * User의 고유키값을 넣어서 디버깅 하기 유용하게 만듬.
     */
    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));
    }
}
