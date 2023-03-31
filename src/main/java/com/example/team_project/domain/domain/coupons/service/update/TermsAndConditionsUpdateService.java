package com.example.team_project.domain.domain.coupons.service.update;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import java.time.LocalDate;
import java.time.Period;

public interface TermsAndConditionsUpdateService {
    void update(CouponKinds couponKinds, Period period);
    void update(CouponKinds couponKinds, LocalDate deadline);
}
