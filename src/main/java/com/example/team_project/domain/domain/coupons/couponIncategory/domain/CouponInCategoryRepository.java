package com.example.team_project.domain.domain.coupons.couponIncategory.domain;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponInCategoryRepository extends JpaRepository<CouponInCategory, Long> {
    void deleteAllByCoupon(Coupon coupon);
    boolean existsByCouponNameAndProductCategory(String couponName, ProductCategory productCategory);
    List<CouponInCategory> findByCouponName(String couponName);
}