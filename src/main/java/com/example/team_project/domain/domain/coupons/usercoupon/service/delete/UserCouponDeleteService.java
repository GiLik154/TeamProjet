package com.example.team_project.domain.domain.coupons.usercoupon.service.delete;

public interface UserCouponDeleteService {
    /**
     * 유저의 ID와 유저가 소유한 쿠폰의 ID를 모두 만족하는
     * 쿠폰을 찾은 후 삭제한다.
     *
     * @param userId   유저의 ID
     * @param userCouponId 유저가 소유한 쿠폰의 ID
     */
    void delete(Long userId, Long userCouponId);
}
