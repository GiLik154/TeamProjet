package com.example.team_project.domain.domain.coupons.coupon.service.add.dto;


import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

@Getter
public class CouponAddServiceDto {
    private final String name;
    private final int discountRate;
    private final int minPrice;
    private final int maxCouponCount;
    private final LocalDate deadline;
    private final Period period;

    public CouponAddServiceDto(String name, int discountRate, int minPrice, int maxCouponCount, LocalDate deadline, Period period) {
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
        this.maxCouponCount = maxCouponCount;
        this.deadline = deadline;
        this.period = period;
    }
}
