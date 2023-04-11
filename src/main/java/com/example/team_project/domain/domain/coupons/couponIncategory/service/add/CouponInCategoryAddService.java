package com.example.team_project.domain.domain.coupons.couponIncategory.service.add;

import com.example.team_project.exception.NotFoundCouponException;
import com.example.team_project.exception.NotMatchCouponCategoryException;

public interface CouponInCategoryAddService {
    /**
     * 쿠폰의 이름으로 쿠폰이 존재하는지 확인한다.
     * 물건의 카테고리 ID로 카테고리가 존재하는지 확인한다.
     * 존재하면 CouponInCategory의 객체를 만들고 JPA로 save한다.
     *
     * @param couponKindsName   쿠폰의 이름
     * @param productCategoryId 물건 카테고리의 ID
     * @throws NotFoundCouponException         쿠폰의 이름으로 쿠폰이 존재하는지 확인할 때
     *                                         존재하지 않으면 발생
     * @throws NotMatchCouponCategoryException 카테고리 ID로 카테고리가 존재하는지 확인할 때
     *                                         존재하지 않으면 발생
     */
    void add(String couponKindsName, Long productCategoryId);
}
