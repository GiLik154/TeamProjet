package com.example.team_project.domain.domain.coupons.usercoupon.service.issue;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;

import java.util.List;

public interface UserIssueAbleCouponListService {
    List<Coupon> getList(Long userId);
}
