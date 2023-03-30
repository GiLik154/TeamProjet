package com.example.team_project.domain.domain.coupons.domain;

import com.example.team_project.exception.InvalidCouponInfo;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CouponKinds {
    @Id
    private String name;

    private int discountRate;

    private int minPrice;

    protected CouponKinds() {
    }

    /**
     * validateParam 메소드에서 입력값 검증이 필요함.
     */
    public CouponKinds(String name, int discountRate, int minPrice) {
        validateParam(discountRate, minPrice);
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
    }

    /**
     * validateParam 메소드에서 입력값 검증이 필요함.
     */
    public void update(int discountRate, int minPrice) {
        validateParam(discountRate, minPrice);
        this.discountRate = discountRate;
        this.minPrice = minPrice;
    }

    /**
     * 만약 discountRate가 0 미만이면 오류 발생
     * 만약 discountRate가 100 초과면 오류 발생
     * 만약 minPrice가 0 이하면 오류 발생
     */
    private void validateParam(int discountRate, int minPrice) {
        if (discountRate >= 100 || discountRate <= 0 || minPrice <= 0) {
            throw new InvalidCouponInfo();
        }
    }
}
