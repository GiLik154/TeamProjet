package com.example.team_project.domain.domain.coupons.service.add.coupon;

import com.example.team_project.domain.domain.coupons.domain.UserHaveCoupon;
import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.domain.UserHaveCouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.ExpiredCouponException;
import com.example.team_project.exception.NotFoundCouponException;
import com.example.team_project.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

@Service
@Transactional
@RequiredArgsConstructor
public class UserHaveCouponAddServiceImpl implements UserHaveCouponAddService {
    private final UserHaveCouponRepository userHaveCouponRepository;
    private final CouponKindsRepository couponKindsRepository;
    private final UserRepository userRepository;

    /**
     * 컨트롤단에서 유저 아이디, 쿠폰 종류의 이름을 받아와서 추가해야함.
     */
    @Override
    public void add(Long userId, String couponKindsName) {
        CouponKinds couponKinds = getCouponKinds(couponKindsName);
        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(getUser(userId), couponKinds);

        setExpirationDate(couponKinds, userHaveCoupon);

        userHaveCouponRepository.save(userHaveCoupon);
    }

    private CouponKinds getCouponKinds(String couponKindsName) {
        return couponKindsRepository.findByName(couponKindsName)
                .orElseThrow(NotFoundCouponException::new);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * 쿠폰의 만료 기간을 설정해야 하는데,
     * 1. Deadline, Period 둘 다 Null 인 경우는 쿠폰 만료일 지정하지 않음.
     * 2. Deadline 만 NUll 인 경우는 ExpirationDate = 현재 날짜 + Period
     * 3. Period 만 NUll 인 경우는 ExpirationDate = Deadline
     * 4. 둘 다 있는 경우, 현재 날자 + Period > Deadline 의 경우 ExpirationDate = Deadline
     * 5. 둘 다 있는 경우, 현재 날자 + Period < Deadline 의 경우 ExpirationDate = 현재 날짜 + Period
     */
    private void setExpirationDate(CouponKinds couponKinds, UserHaveCoupon userHaveCoupon) {
        if (couponKinds.getDeadline() == null && couponKinds.getPeriod() == null) {
            return;
        }

        if (couponKinds.getDeadline() != null && couponKinds.getDeadline().isBefore(LocalDate.now())) {
            throw new ExpiredCouponException();
        }
        userHaveCoupon.setExpirationDate(getExpirationDate(couponKinds.getDeadline(), couponKinds.getPeriod()));
    }

    private LocalDate getExpirationDate(LocalDate deadline, Period period) {
        if (deadline != null && period == null) {
            return deadline;
        } else if (deadline == null) {
            return LocalDate.now().plus(period);
        } else if (deadline.plus(period).isAfter(LocalDate.now())) {
            return deadline;
        }

        return deadline.plus(period);
    }
}
