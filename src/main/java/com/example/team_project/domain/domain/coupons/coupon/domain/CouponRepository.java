package com.example.team_project.domain.domain.coupons.coupon.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, String> {
    Optional<Coupon> findByName(String name);
    List<Coupon> findByDeadlineIsAfter(LocalDate localDate);
}
