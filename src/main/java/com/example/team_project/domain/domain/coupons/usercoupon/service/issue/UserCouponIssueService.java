package com.example.team_project.domain.domain.coupons.usercoupon.service.issue;

import com.example.team_project.exception.NotFoundCouponException;

public interface UserCouponIssueService {
    /**
     * 쿠폰의 이름으로 쿠폰이 존재하는지 확인한다.
     * 존재하면 유저의 ID와 쿠폰의 이름으로
     * 유저가 발급받은 쿠폰의 갯수를 조회한다.
     * 만약, 발급이 가능한 상태라면 쿠폰을 발급한다.
     * (쿠폰의 최대 발급 횟수 보다 작으면)
     *
     * @param userId     유저의 ID
     * @param couponName 쿠폰의 이름
     * @throws NotFoundCouponException 쿠폰의 이름으로 쿠폰이 존재하는지
     *                                 확인할 때 찾지 존재하지 않으면 발생.
     */
    void issue(Long userId, String couponName);
}
