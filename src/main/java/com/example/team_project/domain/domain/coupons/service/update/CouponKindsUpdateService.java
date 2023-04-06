package com.example.team_project.domain.domain.coupons.service.update;

import com.example.team_project.domain.domain.coupons.service.update.dto.CouponUpdateServiceDto;

public interface CouponKindsUpdateService {
    /**
     * 쿠폰의 이름으로 쿠폰이 존재하는지 확인한다.
     * 존재하면 DTO의 정보로 쿠폰의 정보를 수정한다.
     *
     * @param couponName 쿠폰의 이름
     * @param couponUpdateServiceDto 쿠폰을 수정할 정보가 담긴 DTO
     * */
    void update(String couponName, CouponUpdateServiceDto couponUpdateServiceDto);
}
