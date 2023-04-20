package com.example.team_project.domain.domain.coupons.coupon.service.apply;

import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.exception.NotApplyCouponException;
import com.example.team_project.exception.NotFoundCouponException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponApplyServiceImpl implements CouponApplyService {
    private final UserCouponRepository userCouponRepository;
    private final CouponIsApplicableForPriceAndProductService applicable;

    @Override
    public int apply(Long userId, Long couponId, Order order) {
        Coupon coupon = getCoupon(userId, couponId).getCoupon();
        ProductCategory productCategory = order.getOrderToProduct().getProduct().getCategory();
        int totalPrice = order.getOrderToProduct().getTotalPrice();

        if (!applicable.isApplicableForPriceAndProduct(coupon, productCategory, totalPrice)) {
            throw new NotApplyCouponException();
        }

        return totalPrice * (100 - coupon.getDiscountRate()) / 100;
    }

    private UserCoupon getCoupon(Long userId, Long couponId) {
        return userCouponRepository.findByUserIdAndIdAndStatusUnused(userId, couponId).orElseThrow(() ->
                new NotFoundCouponException(("User does not have the coupon. Coupon ID:" + couponId)));
    }
}