package com.example.team_project.domain.domain.coupons.service.add.couponkinds.dto;


import lombok.Getter;

@Getter
public class CouponKindsAddServiceDto {
    private final String name;
    private final int discountRate;
    private final int minPrice;

    public CouponKindsAddServiceDto(String name, int discountRate, int minPrice) {
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
    }
}
