package com.example.team_project.domain.domain.coupons.usercoupon.service.issue;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserIssueAbleCouponListServiceImpl implements UserIssueAbleCouponListService {
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;

    @Override
    public List<Coupon> getList(Long userId) {
        userRepository.validateUserId(userId);

        return couponRepository.findByDeadlineIsAfter(LocalDate.now()).stream()
                .filter(coupon -> canUserGetCoupon(userId, coupon))
                .collect(Collectors.toList());
    }

    private boolean canUserGetCoupon(Long userId, Coupon coupon) {
        int userGetCouponCount = userCouponRepository.countByUserIdAndCouponName(userId, coupon.getName());
        return userGetCouponCount < coupon.getMaxCouponCount();
    }
}
