package com.example.team_project.domain.domain.coupons.service.update;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

@Service
@Transactional
@RequiredArgsConstructor
public class TermsAndConditionsUpdateServiceImpl implements TermsAndConditionsUpdateService {

    @Override
    public void update(CouponKinds couponKinds, Period period) {
        couponKinds.setPeriod(period);
    }

    @Override
    public void update(CouponKinds couponKinds, LocalDate deadline) {
        couponKinds.setDeadline(deadline);
    }
}
