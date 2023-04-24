package com.example.team_project.domain.domain.coupons.coupon.service.add;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.coupon.service.add.dto.CouponAddServiceDto;
import com.example.team_project.domain.domain.coupons.coupon.service.update.TermsAndConditionsUpdateService;
import com.example.team_project.domain.domain.coupons.couponincategory.service.add.CouponInCategoryAddService;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.UserNotCouponLevelException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponAddServiceImpl implements CouponAddService {
    private final TermsAndConditionsUpdateService termsAndConditionsUpdateService;
    private final CouponInCategoryAddService couponInCategoryAddService;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Override
    public void add(Long userID, CouponAddServiceDto couponAddServiceDto) {
        validUser(userID);

        Coupon coupon = new Coupon(couponAddServiceDto.getName(),
                couponAddServiceDto.getDiscountRate(),
                couponAddServiceDto.getMinPrice(),
                couponAddServiceDto.getMaxCouponCount());

        setCouponExpirationPeriod(coupon, couponAddServiceDto);

        couponRepository.save(coupon);

        addCouponCategory(coupon.getName(), couponAddServiceDto.getCategoryIdList());
    }

    public void validUser(Long userId) {
        if (!userRepository.checkUserCouponLevel(userId)) {
            throw new UserNotCouponLevelException("The user tried to force a coupon user ID :" + userId);
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

    private void addCouponCategory(String couponName, List<Long> categoryIdList) {
        categoryIdList.forEach(id ->
                couponInCategoryAddService.add(couponName, id));
    }
}