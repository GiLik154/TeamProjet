package com.example.team_project.domain.domain.coupons.service.expiration;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.exception.ExpiredCouponException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class CouponExpirationCalculatorImpl implements CouponExpirationCalculator {

    /**
     * 쿠폰의 만료 기간을 설정해야 하는데,
     * 1. Deadline, Period 둘 다 Null 인 경우는 쿠폰 만료일 지정하지 않음.
     * 2. 만료된 날자의 쿠폰인 경우에는 ExpiredCouponException 발생.
     * 3. Deadline != Null Period == Null 인 경우는 ExpirationDate = Deadline
     * 3. Deadline == NUll Period != Null 인 경우는 ExpirationDate = 현재 날짜 + Period
     * 4. 둘 다 Null이 아니고 (현재 날자 + Period) > Deadline 의 경우 ExpirationDate = Deadline
     * 5. 둘 다 Null이 아니고 (현재 날자 + Period) < Deadline 의 경우 ExpirationDate = 현재 날짜 + Period
     */

    @Override
    public LocalDate setExpirationDate(CouponKinds couponKinds) {
        if (couponKinds.getDeadline() == null && couponKinds.getPeriod() == null) {
            return LocalDate.of(2999,12,31);
        }

        if (couponKinds.getDeadline() != null && couponKinds.getDeadline().isBefore(LocalDate.now())) {
            throw new ExpiredCouponException();
        }
        return getExpirationDate(couponKinds.getDeadline(), couponKinds.getPeriod());
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
