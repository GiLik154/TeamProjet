package com.example.team_project.domain.domain.coupons.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserHaveCouponRepository extends JpaRepository<UserHaveCoupon, Long> {
    Optional<UserHaveCoupon> findByUserIdAndId(Long userId, Long couponId);

    List<UserHaveCoupon> findByUserId(Long userId);

    Optional<UserHaveCoupon> findByCouponKindsName(String couponKindsName);

    void deleteByUserIdAndId(Long couponId, Long userId);
}
