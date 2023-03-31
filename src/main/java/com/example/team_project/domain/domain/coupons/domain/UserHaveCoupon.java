package com.example.team_project.domain.domain.coupons.domain;

import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class UserHaveCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private CouponKinds couponKinds;

    private LocalDate expirationDate;

    protected UserHaveCoupon() {
    }

    public UserHaveCoupon(User user, CouponKinds couponKinds) {
        this.user = user;
        this.couponKinds = couponKinds;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}