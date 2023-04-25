package com.example.team_project.domain.domain.coupons.coupon.service.apply;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.enums.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CouponIsApplicableForPriceAndProductServiceImpl implements CouponIsApplicableForPriceAndProductService {
    private final CouponInCategoryRepository couponInCategoryRepository;

    @Override
    public boolean isApplicableForPriceAndProduct(UserCoupon userCoupon, ProductCategory productCategory, int totalPrice) {
        Coupon coupon = userCoupon.getCoupon();

        return isValidExpirationDate(userCoupon)
                && isValidStatus(userCoupon)
                && isValidCategory(coupon, productCategory)
                && isValidMinPrice(coupon, totalPrice);
    }

    private boolean isValidExpirationDate(UserCoupon userCoupon) {
        return userCoupon.getExpirationDate().isAfter(LocalDate.now().plusDays(-1));
    }

    private boolean isValidStatus(UserCoupon userCoupon) {
        return userCoupon.getStatus() == CouponStatus.UNUSED;
    }

    private boolean isValidCategory(Coupon coupon, ProductCategory productCategory) {
        return couponInCategoryRepository.existsByCouponAndProductCategory(coupon, productCategory);
    }

    private boolean isValidMinPrice(Coupon coupon, int totalPrice) {
        return coupon.getMinPrice() <= totalPrice;
    }
}