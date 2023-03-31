package com.example.team_project.domain.domain.coupons.service.update;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
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
    /** 쿠폰의 Period을 업데이트 하는 메소드*/
    @Override
    public void update(CouponKinds couponKinds, Period period) {
        couponKinds.updatePeriod(period);
    }
    /** 쿠폰의 deadline을 업데이트 하는 메소드*/
    @Override
    public void update(CouponKinds couponKinds, LocalDate deadline) {
        couponKinds.updateDeadline(deadline);
    }
}
