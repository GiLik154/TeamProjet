package com.example.team_project.domain.domain.coupons.coupon.service.apply;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;

public interface CouponIsApplicableForPriceAndProductService {

    /**
     * 유저의 Id로 사용 가능한 유저의 쿠폰을 조회한다.
     * 물건의 Id로 물건이 속해있는 카테고리를 확인한다.
     * 물건의 카테고리에서 사용이 가능하고, 최저 가격을 만족하는 쿠폰을 List로 반환한다.
     *
     * @return
     */
    boolean isApplicableForPriceAndProduct(Coupon coupon, ProductCategory productCategory, int totalPrice);
}
