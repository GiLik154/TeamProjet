package com.example.team_project.domain.domain.coupons.service.add.coupon;

import com.example.team_project.domain.domain.coupons.domain.Coupon;
import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.domain.CouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.NotFoundCouponException;
import com.example.team_project.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponAddServiceImpl implements CouponAddService {
    private final CouponRepository couponRepository;
    private final CouponKindsRepository couponKindsRepository;
    private final UserRepository userRepository;
    /**
     * 컨트롤단에서 유저 아이디, 쿠폰 종류의 이름을 받아와서 추가해야함.
     */
    @Override
    public void add(Long userId, String couponKindsName) {
        Coupon coupon = new Coupon(getUser(userId), getCouponKinds(couponKindsName));

        couponRepository.save(coupon);
    }

    private CouponKinds getCouponKinds(String couponKindsName) {
        return couponKindsRepository.findByName(couponKindsName)
                .orElseThrow(NotFoundCouponException::new);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}
