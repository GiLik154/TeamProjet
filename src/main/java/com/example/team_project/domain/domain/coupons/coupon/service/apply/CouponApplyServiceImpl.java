package com.example.team_project.domain.domain.coupons.coupon.service.apply;

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
    public int apply(Long userId, Long userCouponId, Order order) {
        UserCoupon userCoupon = getCoupon(userId, userCouponId);
        Coupon coupon = userCoupon.getCoupon();

        int totalPrice = order.getOrderToProduct().getTotalPrice();

        if (!isValidUserCoupon(userCoupon, order, totalPrice)) {
            throw new NotApplyCouponException();
        }

        userCoupon.updateStatusWhenUsed();

        return totalPrice * (100 - coupon.getDiscountRate()) / 100;
    }


    private UserCoupon getCoupon(Long userId, Long userCouponId) {
        return userCouponRepository.findByUserIdAndIdAndStatusUnused(userId, userCouponId).orElseThrow(() ->
                new NotFoundCouponException(("User does not have the coupon. Coupon ID:" + userCouponId)));
    }

    private boolean isValidUserCoupon(UserCoupon userCoupon, Order order, int totalPrice) {
        ProductCategory productCategory = order.getOrderToProduct().getProduct().getCategory();

        return applicable.isApplicableForPriceAndProduct(userCoupon, productCategory, totalPrice);
    }
}