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
    /** 쿠폰의 종류를 수정하는 메소드
     * 할인율과 최저 가격만 수정할 수 있음. */
    @Override
    public void update(String couponKindsName, CouponKindsUpdateServiceDto couponKindsUpdateServiceDto) {
        couponKindsRepository.findByName(couponKindsName).ifPresent(couponKinds ->
                couponKinds.updateApplicableConditions(couponKindsUpdateServiceDto.getDiscountRate(),
                        couponKindsUpdateServiceDto.getMinPrice()));
    }
}
