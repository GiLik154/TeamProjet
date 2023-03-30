package com.example.team_project.domain.domain.coupons.domain;

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

    @OneToOne(fetch = FetchType.LAZY)
    private CouponKinds couponKinds;

    protected Coupon() {
    }

    public Coupon(User user, CouponKinds couponKinds) {
        this.user = user;
        this.couponKinds = couponKinds;
    }

}