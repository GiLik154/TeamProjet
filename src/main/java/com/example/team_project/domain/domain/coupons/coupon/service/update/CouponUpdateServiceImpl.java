package com.example.team_project.domain.domain.coupons.coupon.service.update;

import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.coupon.service.update.dto.CouponUpdateServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponUpdateServiceImpl implements CouponUpdateService {
    private final CouponRepository couponRepository;

    @Override
    public void update(String couponName, CouponUpdateServiceDto couponUpdateServiceDto) {
        couponRepository.findByName(couponName).ifPresent(coupon ->
                coupon.updateDiscountRateAndMinPrice(couponUpdateServiceDto.getDiscountRate(),
                        couponUpdateServiceDto.getMinPrice(),
                        couponUpdateServiceDto.getMaxCouponCount()));
    }
}
