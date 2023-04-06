package com.example.team_project.domain.domain.coupons.service.delete.couponincategory;

import com.example.team_project.domain.domain.coupons.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponInCategoryDeleteServiceImpl implements CouponInCategoryDeleteService {
    private final CouponInCategoryRepository couponInCategoryRepository;

    @Override
    public void deleteByCouponKindsId(Coupon coupon) {
        couponInCategoryRepository.deleteAllByCoupon(coupon);
    }
}