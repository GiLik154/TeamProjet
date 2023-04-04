package com.example.team_project.domain.domain.coupons.service.expiration;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;

import java.time.LocalDate;

public interface CouponExpirationCalculator {
    LocalDate setExpirationDate(CouponKinds couponKinds);
}
