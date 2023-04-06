package com.example.team_project.domain.domain.coupons.service.delete.coupon;

import com.example.team_project.domain.domain.coupons.domain.UserHaveCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserHaveCouponDeleteServiceImpl implements UserHaveCouponDeleteService {
    private final UserHaveCouponRepository userHaveCouponRepository;

    @Override
    public void delete(Long userId, Long couponId) {
        userHaveCouponRepository.deleteByUserIdAndId(userId, couponId);
    }
}
