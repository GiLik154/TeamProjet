package com.example.team_project.domain.domain.coupons.coupon.service.delete;

import com.example.team_project.exception.UserNotCouponLevelException;
import com.example.team_project.exception.UserNotFoundException;

public interface CouponDeleteService {
    /**
     * 유저의 ID로 유저가 존재하는지 확인한다.
     * 존재하면유저가 쿠폰을 삭제할 수 있는지 확인한다.
     * 쿠폰의 이름으로 쿠폰이 존재하는지 확인한다.
     * 쿠폰이 존재하면 CouponInCategoryDeleteService 서비스를 실행해
     * 연관이 있는 모든 맵핑 DB를 삭제한다.
     *
     * @param userID     유저의 ID
     * @param couponName 쿠폰의 이름
     * @throws UserNotFoundException       유저의 ID로 유저가 존재하는지 확인할 때 존재하지 않으면 발생
     * @throws UserNotCouponLevelException 유저의 등급이 쿠폰을 생성하지 못하는 등급일 시 발생.
     */
    void delete(Long userID, String couponName);
}
