package com.example.team_project.domain.domain.coupons.domain;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import lombok.Getter;

import javax.persistence.*;

/** 쿠폰 - 카테고리를 연결해주는 정규화 된 엔티티 */
@Entity
@Getter
public class CouponInCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 쿠폰 종류 */
    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;
    /** 물건의 카테고리 */
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory productCategory;

    protected CouponInCategory() {
    }

    /** 기본 생성자 */
    public CouponInCategory(Coupon coupon, ProductCategory productCategory) {
        this.coupon = coupon;
        this.productCategory = productCategory;
    }

}
