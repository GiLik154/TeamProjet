package com.example.team_project.domain.domain.coupons.service.delete.couponkinds;

import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.service.delete.couponincategory.CouponInCategoryDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponKindsDeleteServiceImpl implements CouponKindsDeleteService {
    private final CouponKindsRepository couponKindsRepository;
    private final CouponInCategoryDeleteService couponInCategoryDeleteService;

    /**
     * PK가 String으로 저장됨 (쿠폰 종류의 이름)
     * CouponInCategoryDeleteService으로 객체를 넘겨줘서
     * CouponKinds 관련된 모든 row 삭제해줘야 함.
     */
    @Override
    public void delete(String couponKindsName) {
        couponKindsRepository.findByName(couponKindsName).ifPresent(couponKinds -> {

            couponInCategoryDeleteService.deleteByCouponKindsId(couponKinds);

            couponKindsRepository.delete(couponKinds);
        });
    }
}
