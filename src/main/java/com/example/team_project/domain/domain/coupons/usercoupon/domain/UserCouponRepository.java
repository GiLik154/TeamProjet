package com.example.team_project.domain.domain.coupons.usercoupon.domain;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUserId(Long userId);

    @Query("SELECT u FROM UserCoupon u WHERE u.user.id = :userId AND u.id = :id AND u.status = '0'")
    Optional<UserCoupon> findByUserIdAndIdAndStatusUnused(@Param("userId") Long userId, @Param("id") Long id);

    @Query("SELECT uc FROM UserCoupon uc JOIN FETCH uc.coupon c JOIN FETCH uc.user u WHERE u.id = :userId AND uc.status = '0'")
    List<UserCoupon> findByUserIdAndStatusUnused(@Param("userId") Long userId);

    @Query("SELECT uc FROM UserCoupon uc JOIN FETCH uc.coupon c JOIN FETCH uc.user u WHERE u.id = :userId AND uc.status = '1'")
    List<UserCoupon> findByUserIdAndStatusUsed(@Param("userId") Long userId);

    @Query("SELECT uc FROM UserCoupon uc JOIN FETCH uc.coupon c JOIN FETCH uc.user u WHERE u.id = :userId AND uc.status = '2'")
    List<UserCoupon> findByUserIdAndStatusExpired(@Param("userId") Long userId);

    @Query("SELECT uc FROM UserCoupon uc JOIN FETCH uc.coupon c JOIN FETCH uc.user u WHERE u.id = :userId")
    List<UserCoupon> findByUserIdForFetchJoin(@Param("userId") Long userId);

    @Query("SELECT uc from UserCoupon uc JOIN FETCH uc.coupon c where uc.id = :id")
    Optional<UserCoupon> findByIdWithCouponFetchJoin(@Param("id") Long id);

    @EntityGraph(attributePaths = "coupon")
    Optional<UserCoupon> findDistinctWithCouponById(Long id);
    Optional<UserCoupon> findByCouponName(String couponName);

    @Query("SELECT u FROM UserCoupon u WHERE u.user.id = :userId AND u.status = '0' AND u.coupon.minPrice <= :price")
    List<UserCoupon> findByUserIdAndCouponMinPriceAfterAndStatusUnused(@Param("userId") Long userId, @Param("price") int price);

    void deleteByUserIdAndId(Long couponId, Long userId);

    // 쿠폰 만료일(expirationDate)이 현재 날짜보다 이전인 쿠폰을 조회
    @Query("SELECT u FROM UserCoupon u WHERE u.expirationDate < :now")
    List<UserCoupon> findExpiredCoupons(@Param("now") LocalDate now);

    int countByUserIdAndCouponName(Long userId, String couponName);
}
