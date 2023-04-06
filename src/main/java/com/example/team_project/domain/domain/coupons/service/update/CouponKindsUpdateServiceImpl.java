package com.example.team_project.domain.domain.coupons.service.update;

import com.example.team_project.domain.domain.coupons.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.service.update.dto.CouponUpdateServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponKindsUpdateServiceImpl implements CouponKindsUpdateService {
    private final CouponRepository couponRepository;
    @Override
    public void update(String couponName, CouponUpdateServiceDto couponUpdateServiceDto) {
        couponRepository.findByName(couponName).ifPresent(couponKinds ->
                couponKinds.updateApplicableConditions(couponUpdateServiceDto.getDiscountRate(),
                        couponUpdateServiceDto.getMinPrice()));
    }
}
