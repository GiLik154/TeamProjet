package com.example.team_project.domain.domain.coupons.usercoupon.service.add;

import com.example.team_project.exception.NotFoundCouponException;
import com.example.team_project.exception.UserNotFoundException;

public interface UserCouponAddService {
    /**
     * 유저의 ID로 유저가 존재하는지 확인한다.
     * 쿠폰의 이름으로 쿠폰이 존재하는지 확인한다.
     * 두 개가 모두 존재하면 쿠폰 종류의 종료일을 참고하여
     * 유저의 쿠폰 마감일을 가지고 온다.
     * 유저의 쿠폰 객체를 만들어 생성하고 JPA로 save한다.
     *
     * @param userId          유저의 ID
     * @param couponName 쿠폰의 이름
     * @throws NotFoundCouponException 쿠폰의 이름으로 쿠폰이 존재하는지 확인할 때
     *                                 존재하지 않으면 발생
     * @throws UserNotFoundException   유저의 ID로 유저가 존재하는지 확인할 때
     *                                 존재하지 않으면 발생
     */
    void add(Long userId, String couponName);
}
