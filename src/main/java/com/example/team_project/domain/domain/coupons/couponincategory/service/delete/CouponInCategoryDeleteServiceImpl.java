package com.example.team_project.domain.domain.coupons.couponincategory.service.delete;

import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponInCategoryDeleteServiceImpl implements CouponInCategoryDeleteService {
    private final CouponInCategoryRepository couponInCategoryRepository;

    @Override
    public void deleteByCouponId(Coupon coupon) {
        couponInCategoryRepository.deleteAllByCoupon(coupon);
    }
}