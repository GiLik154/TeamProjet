package com.example.team_project.domain.domain.coupons.service.add.coupon;

import com.example.team_project.domain.domain.coupons.domain.Coupon;
import com.example.team_project.domain.domain.coupons.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.service.add.coupon.dto.CouponAddServiceDto;
import com.example.team_project.domain.domain.coupons.service.update.TermsAndConditionsUpdateService;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.UserNotCreativeCouponException;
import com.example.team_project.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponAddServiceImpl implements CouponAddService {
    private final TermsAndConditionsUpdateService termsAndConditionsUpdateService;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    private static final UserGrade PERMISSION_COUPON_CREATE_GRADE = UserGrade.VIP;

    @Override
    public void add(Long userID, CouponAddServiceDto couponAddServiceDto) {
        validUser(userID);

        Coupon coupon = new Coupon(couponAddServiceDto.getName(),
                couponAddServiceDto.getDiscountRate(),
                couponAddServiceDto.getMinPrice());

        setCouponExpirationPeriod(coupon, couponAddServiceDto);

        couponRepository.save(coupon);
    }

    public void validUser(Long userId) {

        UserGrade userGrade = userRepository.findById(userId).
                orElseThrow(UserNotFoundException::new).getUserGrade();

        System.out.println(userId + "ASdfasdfasfd");

        if (!userGrade.equals(PERMISSION_COUPON_CREATE_GRADE)) {
            throw new UserNotCreativeCouponException("The user tried to force a coupon user ID :" + userId);
        }
    }

    private void setCouponExpirationPeriod(Coupon coupon, CouponAddServiceDto couponAddServiceDto) {
        setCouponDeadline(coupon, couponAddServiceDto);
        setCouponPeriod(coupon, couponAddServiceDto);
    }

    private void setCouponDeadline(Coupon coupon, CouponAddServiceDto couponAddServiceDto) {
        Optional<LocalDate> deadlineOptional = Optional.ofNullable(couponAddServiceDto.getDeadline());

        deadlineOptional.ifPresent(deadline ->
                termsAndConditionsUpdateService.update(coupon, deadline));
    }

    private void setCouponPeriod(Coupon coupon, CouponAddServiceDto couponAddServiceDto) {
        Optional<Period> periodOptional = Optional.ofNullable(couponAddServiceDto.getPeriod());

        periodOptional.ifPresent(period ->
                termsAndConditionsUpdateService.update(coupon, period));
    }
}

