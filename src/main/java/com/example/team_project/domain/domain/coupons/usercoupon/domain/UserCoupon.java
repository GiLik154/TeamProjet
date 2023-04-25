package com.example.team_project.domain.domain.coupons.usercoupon.domain;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.enums.CouponStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.repository.EntityGraph;

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

    /**
     * 쿠폰의 상태
     * (사용 전, 사용 완료, 만료됨)
     */
    @Enumerated(EnumType.ORDINAL)
    private CouponStatus status;

    /**
     * 쿠폰이 사용된 날짜
     */
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

    public void updateStatusWhenUsed() {
        this.status = CouponStatus.USED;
        this.usedDate = LocalDate.now();
    }

    public void updateStatus(CouponStatus couponStatus) {
        this.status = couponStatus;
    }
}