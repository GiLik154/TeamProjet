package com.example.team_project.domain.domain.coupons.coupon.service.update;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;

import java.time.LocalDate;
import java.time.Period;

public interface TermsAndConditionsUpdateService {
    /**
     * 쿠폰에 Period 정보를 업데이트 한다.
     *
     * @param coupon 기한을 설정할 쿠폰
     * @param period 쿠폰의 사용 일 (ex 7일동안 사용가능.)
     */
    void update(Coupon coupon, Period period);

    /**
     * 쿠폰에 deadline 정보를 업데이트 한다.
     *
     * @param coupon   기한을 설정할 쿠폰
     * @param deadline 쿠폰 사용의 마지막 날짜
     */
    void update(Coupon coupon, LocalDate deadline);
}
