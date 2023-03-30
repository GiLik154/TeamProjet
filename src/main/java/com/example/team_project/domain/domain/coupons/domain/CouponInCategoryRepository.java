package com.example.team_project.domain.domain.coupons.domain;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponInCategoryRepository extends JpaRepository<CouponInCategory, Long> {
    void deleteAllByCouponKinds(CouponKinds couponKinds);

    void deleteAllByProductCategory(ProductCategory productCategory);

    List<CouponInCategory> findByCouponKindsName(String couponKindsName);
}