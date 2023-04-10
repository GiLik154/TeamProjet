package com.example.team_project.domain.domain.coupons.coupon.service.add;

import com.example.team_project.domain.domain.coupons.coupon.service.add.dto.CouponAddServiceDto;

public interface CouponAddService {
    /**
     * 유저의 ID를 통해 유저가 존재하는지 확인한다.
     * 존재하면, 유저의 등급을 비교하여 쿠폰을 생성할 수 있는지 확인한다.
     * DTO를 이용해서 Coupon의 객체를 생성한다.
     * DTO의 Deadline과 Period의 여부에 따라 쿠폰 종류일을 추가한다.
     * 이후 JPA를 통해 save한다.
     *
     * @param userId              유저의 ID
     * @param couponAddServiceDto 쿠폰을 생성하는데 필요한 정보가 담긴 Dto
     */
    void add(Long userId, CouponAddServiceDto couponAddServiceDto);
}
