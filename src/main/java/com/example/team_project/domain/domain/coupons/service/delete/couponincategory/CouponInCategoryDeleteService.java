package com.example.team_project.domain.domain.coupons.service.delete.couponincategory;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;

public interface CouponInCategoryDeleteService {
    void deleteByCouponKindsId(CouponKinds couponKinds);
}
