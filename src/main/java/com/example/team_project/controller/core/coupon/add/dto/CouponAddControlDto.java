package com.example.team_project.controller.core.coupon.add.dto;


import com.example.team_project.domain.domain.coupons.coupon.service.add.dto.CouponAddServiceDto;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Getter
public class CouponAddControlDto {
    @NotEmpty
    private final String name;
    @Min(0)
    @Max(100)
    private final int discountRate;
    @Min(0)
    @Max(100000000)
    private final int minPrice;
    private final int maxCouponCount;
    private final LocalDate deadline;
    private final Period period;
    private final List<Long> categoryIdList;

    public CouponAddControlDto(String name, int discountRate, int minPrice, int maxCouponCount, String deadline, Period period, List<Long> categoryIdList) {
        if (deadline == null) {
            deadline = String.valueOf(LocalDate.of(2099, 12, 31));
        }
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
        this.maxCouponCount = maxCouponCount;
        this.deadline = LocalDate.parse(deadline);
        this.period = period;
        this.categoryIdList = categoryIdList;
    }

    public CouponAddServiceDto convertServiceDto() {
        return new CouponAddServiceDto(this.name, this.discountRate, this.minPrice, this.maxCouponCount, this.deadline, this.period, this.categoryIdList);
    }
}
