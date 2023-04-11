package com.example.team_project.domain.domain.coupons.coupon.service.update;

public interface ExpiredCouponUpdateService {
    /**
     * 기간이 만료된 쿠폰을 자동으로 삭제한다.
     * 모든 유저의 쿠폰을 찾아온 후
     * 기한이 지난 쿠폰의 상태를 EXPIRED로 변환한다.
     */
    void delete();
}
