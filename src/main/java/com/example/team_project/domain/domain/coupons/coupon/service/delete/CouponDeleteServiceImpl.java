package com.example.team_project.domain.domain.coupons.coupon.service.delete;

import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.couponIncategory.service.delete.CouponInCategoryDeleteService;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.UserNotCouponLevelException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponDeleteServiceImpl implements CouponDeleteService {
    private final CouponRepository couponRepository;
    private final CouponInCategoryDeleteService couponInCategoryDeleteService;
    private final UserRepository userRepository;

    @Override
    public void delete(Long userId, String couponName) {
        validUser(userId);

        couponRepository.findByName(couponName).ifPresent(couponKinds -> {
            couponInCategoryDeleteService.deleteByCouponId(couponKinds);
            couponRepository.delete(couponKinds);
        });
    }

    public void validUser(Long userId) {
        if (!userRepository.checkUserCouponLevel(userId)) {
            throw new UserNotCouponLevelException("The user tried to force a coupon user ID : " + userId);
        }
    }
}
