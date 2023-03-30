package com.example.team_project.domain.domain.coupons.service.apply;

import com.example.team_project.domain.domain.coupons.domain.*;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.exception.NotApplyCouponException;
import com.example.team_project.exception.NotFoundCouponException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponApplyServiceImpl implements CouponApplyService {
    private final UserHaveCouponRepository userHaveCouponRepository;
    private final CouponInCategoryRepository couponInCategoryRepository;

    @Override
    public int apply(Long userId, Long couponId, Order order) {
        CouponKinds coupon = getCoupon(userId, couponId).getCouponKinds();

        if (!applyAble(coupon, order)) {
            throw new NotApplyCouponException();
        }

        return order.getTotalPrice() * (100 - coupon.getDiscountRate()) / 100;
    }

    private UserHaveCoupon getCoupon(Long userId, Long couponId) {
        return userHaveCouponRepository.findByUserIdAndId(userId, couponId).orElseThrow(NotFoundCouponException::new);
    }

    private boolean applyAble(CouponKinds coupon, Order order) {
        return isApplicableToCategory(coupon, order) &&
                isMinimumPriceSatisfied(order, coupon.getMinPrice());
    }

    private boolean isApplicableToCategory(CouponKinds coupon, Order order) {
        List<CouponInCategory> categories = couponInCategoryRepository.findByCouponKindsName(coupon.getName());

        return categories.stream().
                anyMatch(category -> category.getProductCategory().equals(order.getProduct().getCategory()));
    }

    private boolean isMinimumPriceSatisfied(Order order, int couponMinPrice) {
        return order.getTotalPrice() >= couponMinPrice;
    }
}