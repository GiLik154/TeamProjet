package com.example.team_project.domain.domain.coupons.coupon.service.apply;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@Transactional
@RequiredArgsConstructor
public class CouponIsApplicableForPriceAndProductServiceImpl implements CouponIsApplicableForPriceAndProductService {
    private final CouponInCategoryRepository couponInCategoryRepository;

    @Override
    public boolean isApplicableForPriceAndProduct(Coupon coupon, ProductCategory productCategory, int totalPrice) {
        if(coupon.getDeadline().isBefore(LocalDate.now())){
            return false;
        }

        return coupon.getMinPrice() <= totalPrice
                && couponInCategoryRepository.existsByCouponAndProductCategory(coupon, productCategory);
    }
}