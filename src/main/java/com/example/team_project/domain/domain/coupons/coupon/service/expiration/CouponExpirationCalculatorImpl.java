package com.example.team_project.domain.domain.coupons.coupon.service.expiration;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.exception.ExpiredCouponException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class CouponExpirationCalculatorImpl implements CouponExpirationCalculator {

    @Override
    public LocalDate setExpirationDate(Coupon coupon) {
        if (coupon.getDeadline() == null && coupon.getPeriod() == null) {
            return LocalDate.of(2999, 12, 31);
        }

        if (coupon.getDeadline() != null && coupon.getDeadline().isBefore(LocalDate.now())) {
            throw new ExpiredCouponException();
        }
        return getExpirationDate(coupon.getDeadline(), coupon.getPeriod());
    }

    private LocalDate getExpirationDate(LocalDate deadline, Period period) {
        if (deadline != null && period == null) {
            return deadline;
        } else if (deadline == null && period != null) {
            return LocalDate.now().plus(period);
        }
        return deadline;
    }
}