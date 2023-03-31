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

    /**
     * 유저에 있는 쿠폰 삭제
     * 유저아이디와 쿠폰 아이디를 같이 검증해야 유저의 쿠폰 삭제가 가능함
     */
    @Override
    public void delete(Long userId, Long couponId) {
        userHaveCouponRepository.deleteByUserIdAndId(userId, couponId);
    }
}
