package com.example.team_project.domain.domain.coupons.domain;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CouponInCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CouponKinds couponKinds;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory productCategory;

    protected CouponInCategory() {
    }

    public CouponInCategory(CouponKinds couponKinds, ProductCategory productCategory) {
        this.couponKinds = couponKinds;
        this.productCategory = productCategory;
    }

}
