package com.example.team_project.domain.domain.coupons.coupon.service.expiration;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.exception.ExpiredCouponException;

import java.time.LocalDate;

public interface CouponExpirationCalculator {
    /**
     * 쿠폰의 만료기한을 설정한다.
     * 1. Deadline, Period 둘 다 Null 인 경우는 쿠폰 만료일 지정하지 않음.
     * 2. Deadline != Null Period == Null 인 경우는 ExpirationDate = Deadline
     * 3. Deadline == NUll Period != Null 인 경우는 ExpirationDate = 현재 날짜 + Period
     * 4. 둘 다 Null이 아니고 (현재 날자 + Period) > Deadline 의 경우 ExpirationDate = Deadline
     * 5. 둘 다 Null이 아니고 (현재 날자 + Period) < Deadline 의 경우 ExpirationDate = 현재 날짜 + Period
     *
     * @param coupon 만료일을 지정할 쿠폰
     * @return 설정된 쿠폰의 만료일
     * @throws ExpiredCouponException 쿠폰의 만료일이 오늘의 날짜보다 이전이면 발생한다.
     */
    LocalDate setExpirationDate(Coupon coupon);
}
