package com.example.team_project.domain.domain.coupons.service.update.dto;


import lombok.Getter;

@Getter
public class CouponKindsUpdateServiceDto {
    /** 쿠폰의 할인 비율 ( 퍼센트 ) */
    private final int discountRate;
    /** 쿠폰의 최저 가격 */
    private final int minPrice;
    /** 쿠폰 종류를 업데이트 할 수 있음.
     * 이 경우 할인 비율과 최저가격만 수정 가능함. */
    public CouponKindsUpdateServiceDto(int discountRate, int minPrice) {
        this.discountRate = discountRate;
        this.minPrice = minPrice;
    }
}
