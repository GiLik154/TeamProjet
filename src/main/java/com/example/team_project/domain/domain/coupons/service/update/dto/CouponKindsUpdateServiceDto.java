package com.example.team_project.domain.domain.coupons.service.update.dto;


import lombok.Getter;

@Getter
public class CouponKindsUpdateServiceDto {
    private final int discountRate;
    private final int minPrice;

    public CouponKindsUpdateServiceDto(int discountRate, int minPrice) {
        this.discountRate = discountRate;
        this.minPrice = minPrice;
    }
}
