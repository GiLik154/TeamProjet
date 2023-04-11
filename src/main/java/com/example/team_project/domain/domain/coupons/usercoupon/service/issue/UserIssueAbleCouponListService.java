package com.example.team_project.domain.domain.coupons.usercoupon.service.issue;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;

import java.util.List;

public interface UserIssueAbleCouponListService {
    /**
     * 만료기한이 지나지 않은 쿠폰을 모두 조회한다.
     * 유저의 ID로 유저가 가지고 있는 쿠폰을 조회한다.
     * 조회한 쿠폰의 최대 보유 수량과
     * 유저의 ID로 유저가 가지고 있는 쿠폰의 수량을 비교한다.
     * 조회한 쿠폰의 최대 수량이 유저가 보유한 쿠폰의 수량보다 작으면
     * 유저가 발급 가능한 쿠폰 목록을 반환한다.
     *
     * @param userId 유저의 ID
     * @return 유저가 발급 가능한 쿠폰 목록
     */
    List<Coupon> getList(Long userId);
}
