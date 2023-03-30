package com.example.team_project.domain.domain.coupons.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCouponKindsName(String couponKindsName);
    void deleteByUserIdAndId(Long couponId, Long userId);
}
