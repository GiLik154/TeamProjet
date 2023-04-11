package com.example.team_project.domain.domain.coupons.usercoupon.service.delete;

import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCouponDeleteServiceImpl implements UserCouponDeleteService {
    private final UserCouponRepository userCouponRepository;

    @Override
    public void delete(Long userId, Long userCouponId) {
        userCouponRepository.deleteByUserIdAndId(userId, userCouponId);
    }
}
