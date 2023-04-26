package com.example.team_project.domain.domain.coupons.coupon.service.add.dto;


import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Getter
public class CouponAddServiceDto {
    private final String name;
    private final int discountRate;
    private final int minPrice;
    private final int maxCouponCount;
    private final LocalDate deadline;
    private final Period period;
    private final List<Long> categoryIdList;

    public CouponAddServiceDto(String name, int discountRate, int minPrice, int maxCouponCount, LocalDate deadline, Period period, List<Long> categoryIdList) {
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
        this.maxCouponCount = maxCouponCount;
        this.deadline = deadline;
        this.period = period;
        this.categoryIdList = categoryIdList;
    }
}
