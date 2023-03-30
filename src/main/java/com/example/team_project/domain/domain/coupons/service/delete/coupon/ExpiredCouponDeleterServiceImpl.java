package com.example.team_project.domain.domain.coupons.service.delete.coupon;

import com.example.team_project.domain.domain.coupons.domain.UserHaveCoupon;
import com.example.team_project.domain.domain.coupons.domain.UserHaveCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpiredCouponDeleterServiceImpl implements ExpiredCouponDeleterService {
    private final UserHaveCouponRepository userHaveCouponRepository;

    @Override
    public void delete(Long userId) {
        List<UserHaveCoupon> coupons = userHaveCouponRepository.findByUserId(userId);

        for (UserHaveCoupon coupon : coupons) {
            if (isTermsAndConditions(coupon)) {
                userHaveCouponRepository.delete(coupon);
            }
        }
    }

    private boolean isTermsAndConditions(UserHaveCoupon coupon) {
        return coupon.getExpirationDate().isBefore(LocalDate.now());
    }
}
