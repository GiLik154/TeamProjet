package com.example.team_project.domain.domain.coupons.service.update;

import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.service.update.dto.CouponKindsUpdateServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponKindsUpdateServiceImpl implements CouponKindsUpdateService {
    private final CouponKindsRepository couponKindsRepository;

    @Override
    public void update(String couponKindsName, CouponKindsUpdateServiceDto couponKindsUpdateServiceDto) {
        couponKindsRepository.findByName(couponKindsName).ifPresent(couponKinds ->
                couponKinds.update(couponKindsUpdateServiceDto.getDiscountRate(),
                        couponKindsUpdateServiceDto.getMinPrice()));
    }
}
