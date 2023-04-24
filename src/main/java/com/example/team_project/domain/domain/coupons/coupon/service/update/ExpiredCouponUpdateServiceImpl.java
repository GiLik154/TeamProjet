package com.example.team_project.domain.domain.coupons.coupon.service.update;

import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.enums.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpiredCouponUpdateServiceImpl implements ExpiredCouponUpdateService {
    private final UserCouponRepository userCouponRepository;

    @Override
    public void delete() {
        List<UserCoupon> expiredCoupons = userCouponRepository.findExpiredCoupons(LocalDate.now());

        expiredCoupons.forEach(userCoupon ->
                userCoupon.updateStatus(CouponStatus.EXPIRED));
    }
}