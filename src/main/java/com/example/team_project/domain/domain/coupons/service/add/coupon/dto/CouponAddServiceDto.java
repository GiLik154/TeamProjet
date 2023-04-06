package com.example.team_project.domain.domain.coupons.service.add.coupon.dto;


import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

@Getter
public class CouponAddServiceDto {
    private final String name;
    private final int discountRate;
    private final int minPrice;
    private final LocalDate deadline;
    private final Period period;

    public CouponAddServiceDto(String name, int discountRate, int minPrice, LocalDate deadline, Period period) {
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
        this.deadline = deadline;
        this.period = period;
    }
}
