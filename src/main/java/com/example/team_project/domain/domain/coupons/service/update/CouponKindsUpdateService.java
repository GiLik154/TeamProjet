package com.example.team_project.domain.domain.coupons.service.update;

import com.example.team_project.domain.domain.coupons.service.update.dto.CouponKindsUpdateServiceDto;

public interface CouponKindsUpdateService {
    void update(String couponKindsName, CouponKindsUpdateServiceDto couponKindsUpdateServiceDto);
}
