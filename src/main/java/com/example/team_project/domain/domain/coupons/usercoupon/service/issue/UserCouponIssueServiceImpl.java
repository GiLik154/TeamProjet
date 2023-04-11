package com.example.team_project.domain.domain.coupons.usercoupon.service.issue;

import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.service.add.UserCouponAddService;
import com.example.team_project.exception.NotFoundCouponException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCouponIssueServiceImpl implements UserCouponIssueService {
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserCouponAddService userCouponAddService;

    @Override
    public void issue(Long userId, String couponName) {
        if (isIssue(userId, couponName)) {
            userCouponAddService.add(userId, couponName);
        }
    }

    private boolean isIssue(Long userId, String couponName) {
        int userCouponCount = userCouponRepository.countByUserIdAndCouponName(userId, couponName);
        int maxCouponCount = getMaxCouponCount(couponName);

        return userCouponCount < maxCouponCount;
    }

    private int getMaxCouponCount(String couponName) {
        return couponRepository.findByName(couponName).orElseThrow(() ->
                new NotFoundCouponException("Coupon not found: " + couponName)).getMaxCouponCount();
    }
}
