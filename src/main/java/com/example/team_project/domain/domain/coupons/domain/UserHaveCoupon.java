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
    /** 쿠폰을 가지고 있을 유저의 정보 */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    /** 유저가 가지고 있는 쿠폰의 종류 */
    @OneToOne(fetch = FetchType.LAZY)
    private CouponKinds couponKinds;
    /** 쿠폰의 사용 기한 */
    private LocalDate expirationDate;

    protected UserHaveCoupon() {
    }
    /** 쿠폰의 기본 생성자 */
    public UserHaveCoupon(User user, CouponKinds couponKinds) {
        this.user = user;
        this.couponKinds = couponKinds;
    }
    /** 쿠폰의 사용 기한을 업데이트 */
    public void updateExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}