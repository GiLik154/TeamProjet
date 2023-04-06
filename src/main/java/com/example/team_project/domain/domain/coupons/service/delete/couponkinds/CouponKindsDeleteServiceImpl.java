package com.example.team_project.domain.domain.coupons.service.delete.couponkinds;

import com.example.team_project.domain.domain.coupons.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.service.delete.couponincategory.CouponInCategoryDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponKindsDeleteServiceImpl implements CouponKindsDeleteService {
    private final CouponRepository couponRepository;
    private final CouponInCategoryDeleteService couponInCategoryDeleteService;

    @Override
    public void delete(String couponName) {
        couponRepository.findByName(couponName).ifPresent(couponKinds -> {

            couponInCategoryDeleteService.deleteByCouponKindsId(couponKinds);

            couponRepository.delete(couponKinds);
        });
    }
}
