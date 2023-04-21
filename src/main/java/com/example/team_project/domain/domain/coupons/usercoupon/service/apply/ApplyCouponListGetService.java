package com.example.team_project.domain.domain.coupons.usercoupon.service.apply;

import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;

import java.util.List;

public interface ApplyCouponListGetService {
    List<UserCoupon> getList(Long userId, Long productId, int totalPrice);
}
