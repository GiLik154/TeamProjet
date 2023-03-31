package com.example.team_project.domain.domain.coupons.service.delete.coupon;

import com.example.team_project.domain.domain.coupons.domain.UserHaveCoupon;
import com.example.team_project.domain.domain.coupons.domain.UserHaveCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpiredCouponDeleterServiceImpl implements ExpiredCouponDeleterService {
    private final UserHaveCouponRepository userHaveCouponRepository;

    /**
     * 기간이 만료된 쿠폰을 삭제함.
     * isCouponExpired를 통해서 오늘 날짜와
     * 쿠폰의 ExpirationDate를 비교해서 삭제함.
     * 모든 쿠폰을 삭제해야 하기 때문에 userId를 통해서
     * UserHaveCoupon을 List로 받아와서 스트림으로 삭제함.
     */
    @Override
    public void delete(Long userId) {
        List<UserHaveCoupon> coupons = userHaveCouponRepository.findByUserId(userId);

        List<UserHaveCoupon> expiredCoupons = coupons.stream()
                .filter(this::isCouponExpired)
                .collect(Collectors.toList());

        userHaveCouponRepository.deleteAll(expiredCoupons);
    }
    /** 쿠폰의 만료일을 계산함.
     * 현재의 날짜에 따라 삭제됨. */
    private boolean isCouponExpired(UserHaveCoupon coupon) {
        return coupon.getExpirationDate().isBefore(LocalDate.now());
    }
}