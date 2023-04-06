package com.example.team_project.domain.domain.coupons.service.delete.coupon;

public interface UserHaveCouponDeleteService {
    /**
     * 유저의 ID와 쿠폰의 ID를 모두 만족하는
     * 쿠폰을 찾은 후 삭제한다.
     *
     * @param userId   유저의 ID
     * @param couponId 쿠폰의 ID
     */
    void delete(Long userId, Long couponId);
}
