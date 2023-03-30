package com.example.team_project.domain.domain.coupons.service.delete.couponincategory;

import com.example.team_project.domain.domain.coupons.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponInCategoryDeleteServiceImpl implements CouponInCategoryDeleteService {
    private final CouponInCategoryRepository couponInCategoryRepository;

    /**
     * couponKinds와 연관되어 있는 모든 맵핑 DB를 삭제함.
     */
    @Override
    public void deleteByCouponKindsId(CouponKinds couponKinds) {
        couponInCategoryRepository.deleteAllByCouponKinds(couponKinds);
    }
}