package com.example.team_project.domain.domain.coupons.service.add.couponkinds.dto;


import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

@Getter
public class CouponKindsAddServiceDto {
    private final String name;
    private final int discountRate;
    private final int minPrice;
    private LocalDate deadline;
    private Period period;


    public CouponKindsAddServiceDto(String name, int discountRate, int minPrice) {
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
    }

    public CouponKindsAddServiceDto(String name, int discountRate, int minPrice, LocalDate deadline) {
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
        this.deadline = deadline;
    }

    public CouponKindsAddServiceDto(String name, int discountRate, int minPrice, Period period) {
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
        this.period = period;
    }

    public CouponKindsAddServiceDto(String name, int discountRate, int minPrice, LocalDate deadline, Period period) {
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
        this.deadline = deadline;
        this.period = period;
    }
}
