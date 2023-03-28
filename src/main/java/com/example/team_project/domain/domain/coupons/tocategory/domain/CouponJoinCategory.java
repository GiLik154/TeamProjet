package com.example.team_project.domain.domain.coupons.tocategory.domain;

import com.example.team_project.domain.domain.coupons.kinds.domain.CouponKinds;
import com.example.team_project.domain.domain.product.domain.category.ProductCategory;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CouponJoinCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private CouponKinds couponKinds;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory productCategory;
}
