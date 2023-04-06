package com.example.team_project.domain.domain.coupons.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserHaveCouponRepository extends JpaRepository<UserHaveCoupon, Long> {
    Optional<UserHaveCoupon> findByUserIdAndId(Long userId, Long couponId);

    List<UserHaveCoupon> findByUserId(Long userId);

    Optional<UserHaveCoupon> findByCouponName(String couponKindsName);

    void deleteByUserIdAndId(Long couponId, Long userId);

    // 쿠폰 만료일(expirationDate)이 현재 날짜보다 이전인 쿠폰을 조회
    @Query("SELECT u FROM UserHaveCoupon u WHERE u.expirationDate < :now")
    List<UserHaveCoupon> findExpiredCoupons(@Param("now") LocalDate now);
}
