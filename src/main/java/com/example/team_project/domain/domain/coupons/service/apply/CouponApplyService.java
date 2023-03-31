package com.example.team_project.domain.domain.coupons.service.apply;


import com.example.team_project.domain.domain.order.item.domain.Order;

public interface CouponApplyService {
    int apply(Long userId, Long couponId, Order order);
}
