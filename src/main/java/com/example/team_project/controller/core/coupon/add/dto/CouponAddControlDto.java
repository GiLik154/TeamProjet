package com.example.team_project.controller.core.coupon.add.dto;


import com.example.team_project.domain.domain.coupons.coupon.service.add.dto.CouponAddServiceDto;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;

@Getter
public class CouponAddControlDto {
    @NotEmpty
    private final String name;
    @NotNull
    private final int discountRate;
    @NotNull
    private final int minPrice;
    private final int maxCouponCount;
    private final LocalDate deadline;
    private final Period period;

    public CouponAddControlDto(String name, int discountRate, int minPrice, int maxCouponCount, LocalDate deadline, Period period) {
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
        this.maxCouponCount = maxCouponCount;
        this.deadline = deadline;
        this.period = period;
    }

    public CouponAddServiceDto convertServiceDto() {
        return new CouponAddServiceDto(this.name, this.discountRate, this.minPrice, this.maxCouponCount, this.deadline, this.period);
    }
}
