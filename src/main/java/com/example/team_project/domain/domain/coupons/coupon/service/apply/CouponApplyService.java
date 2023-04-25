package com.example.team_project.domain.domain.coupons.coupon.service.apply;


import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.exception.NotApplyCouponException;
import com.example.team_project.exception.NotFoundCouponException;

public interface CouponApplyService {
    /**
     * 유저의 ID와 쿠폰의 ID로 쿠폰이 존재하는지 확인한다.
     * 존재하면 적용이 가능한 쿠폰인지 비교한다.
     * 적용이 가능하면 오더의 가격에 쿠폰의 할인율을 적용한다.
     *
     * @param userId   유저의 ID
     * @param userCouponId 쿠폰의 ID
     * @param order    쿠폰을 적용할 주문
     * @return 주문의 총 가격에 쿠폰의 할인율을 적용해서 리턴
     * @throws NotFoundCouponException 유저의 ID와 쿠폰의 ID로 쿠폰이 존재하는지
     *                                 확인할 때 존재하지 않으면 발생.
     * @throws NotApplyCouponException 쿠폰의 적용 여부를 확인할 때
     *                                 적용할 수 없으면 발생.
     */
    int apply(Long userId, Long userCouponId, Order order);
}
