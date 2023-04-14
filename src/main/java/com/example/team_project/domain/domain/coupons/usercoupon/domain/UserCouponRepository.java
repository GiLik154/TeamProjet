package com.example.team_project.domain.domain.coupons.usercoupon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    @Query("SELECT u FROM UserCoupon u WHERE u.user.id = :userId AND u.id = :id AND u.status = '0'")
    Optional<UserCoupon> findByUserIdAndIdAndStatusUnused(@Param("userId") Long userId, @Param("id") Long id);

    @Query("SELECT u FROM UserCoupon u WHERE u.user.id = :userId AND u.status = '0'")
    List<UserCoupon> findByUserIdAndStatusUnused(@Param("userId") Long userId);

    @Query("SELECT u FROM UserCoupon u WHERE u.user.id = :userId AND u.status = '1'")
    List<UserCoupon> findByUserIdAndStatusUsed(@Param("userId") Long userId);

    @Query("SELECT u FROM UserCoupon u WHERE u.user.id = :userId AND u.status = '2'")
    List<UserCoupon> findByUserIdAndStatusExpired(@Param("userId") Long userId);

    List<UserCoupon> findByUserId(Long userId);

    Optional<UserCoupon> findByCouponName(String couponName);

    void deleteByUserIdAndId(Long couponId, Long userId);

    // 쿠폰 만료일(expirationDate)이 현재 날짜보다 이전인 쿠폰을 조회
    @Query("SELECT u FROM UserCoupon u WHERE u.expirationDate < :now")
    List<UserCoupon> findExpiredCoupons(@Param("now") LocalDate now);

    int countByUserIdAndCouponName(Long userId, String couponName);
}
