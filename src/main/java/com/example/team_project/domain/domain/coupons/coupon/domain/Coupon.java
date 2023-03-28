package com.example.team_project.domain.domain.coupons.coupon.domain;

import com.example.team_project.domain.domain.coupons.kinds.domain.CouponKinds;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne
    private CouponKinds couponKinds;
}