package com.example.team_project.domain.domain.coupons.coupon.service.update;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

/** 쿠폰의 기한을 업데이트 하는 서비스.
 * 오버로딩으로 들어오는 타입이 Period인지, LocalDate인지에 따라서
 * 다르게 작동됨.*/
@Service
@Transactional
@RequiredArgsConstructor
public class TermsAndConditionsUpdateServiceImpl implements TermsAndConditionsUpdateService {
    @Override
    public void update(Coupon coupon, Period period) {
        coupon.updatePeriod(period);
    }
    @Override
    public void update(Coupon coupon, LocalDate deadline) {
        coupon.updateDeadline(deadline);
    }
}
