package com.example.team_project.domain.domain.coupons.service.delete.couponkinds;

public interface CouponKindsDeleteService {
    /**
     * 쿠폰의 이름으로 쿠폰이 존재하는지 확인한다.
     * 쿠폰이 존재하면 CouponInCategoryDeleteService 서비스를 실행해
     * 연관이 있는 모든 맵핑 DB를 삭제한다.
     *
     * @param couponName 쿠폰의 이름
     */
    void delete(String couponName);
}
