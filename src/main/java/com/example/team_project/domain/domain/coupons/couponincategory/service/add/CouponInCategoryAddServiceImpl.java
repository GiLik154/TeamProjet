package com.example.team_project.domain.domain.coupons.couponincategory.service.add;

import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategory;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.exception.NotFoundCouponException;
import com.example.team_project.exception.NotMatchCouponCategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponInCategoryAddServiceImpl implements CouponInCategoryAddService {
    private final CouponRepository couponRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CouponInCategoryRepository couponInCategoryRepository;

    @Override
    public void add(String couponName, Long productCategoryId) {
        CouponInCategory couponInCategory = new CouponInCategory(
                getCouponKinds(couponName),
                getProductCategory(productCategoryId)
        );
        couponInCategoryRepository.save(couponInCategory);
    }

    private Coupon getCouponKinds(String couponKindsName) {
        return couponRepository.findById(couponKindsName).orElseThrow(() ->
                new NotFoundCouponException("Coupon kinds not found: " + couponKindsName));
    }

    private ProductCategory getProductCategory(Long productCategoryId) {
        return productCategoryRepository.findById(productCategoryId).orElseThrow(
                NotMatchCouponCategoryException::new
        );
    }
}
