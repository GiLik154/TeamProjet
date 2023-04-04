package com.example.team_project.domain.domain.coupons.service.delete.coupon;

import com.example.team_project.domain.domain.coupons.domain.UserHaveCoupon;
import com.example.team_project.domain.domain.coupons.domain.UserHaveCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpiredCouponDeleterServiceImpl implements ExpiredCouponDeleterService {
    private final UserHaveCouponRepository userHaveCouponRepository;

    /**
     * 기간이 만료된 쿠폰을 삭제함.
     * findExpiredCoupons.를 통해서 DB에서 삭제해야 하는 모든 DB를 가지고옴
     * 이후 deleteAll로 모두 삭제하는데
     * 이렇게 하는 이유는, 트랜잭션이 무거워지는 것을 막기 위해서임
     */
    @Override
    public void delete() {
        List<UserHaveCoupon> deleteList = userHaveCouponRepository.findExpiredCoupons(LocalDate.now());

        userHaveCouponRepository.deleteAll(deleteList);
    }
}