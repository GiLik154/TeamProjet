package com.example.team_project.domain.domain.coupons.usercoupon.domain;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.enums.CouponStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(indexes = {@Index(name = "expirationDate_index", columnList = "expiration_date")})
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 쿠폰을 가지고 있을 유저의 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    /**
     * 유저가 가지고 있는 쿠폰의 종류
     */
    @OneToOne(fetch = FetchType.LAZY)
    private Coupon coupon;
    /**
     * 쿠폰의 사용 기한
     */
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "status")
    private CouponStatus status;

    private LocalDate usedDate;

    protected UserCoupon() {
    }

    /**
     * 쿠폰의 기본 생성자
     */
    public UserCoupon(User user, Coupon coupon, LocalDate expirationDate) {
        this.user = user;
        this.coupon = coupon;
        this.expirationDate = expirationDate;
        this.status = CouponStatus.UNUSED;
    }

    /**
     * 쿠폰의 사용 기한을 업데이트
     */
    public void updateExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void updateStatus(CouponStatus couponStatus) {
        this.status = couponStatus;
    }
}