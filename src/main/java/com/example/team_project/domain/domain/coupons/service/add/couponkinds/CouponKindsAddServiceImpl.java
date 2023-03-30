package com.example.team_project.domain.domain.coupons.service.add.couponkinds;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.service.add.couponkinds.dto.CouponKindsAddServiceDto;
import com.example.team_project.domain.domain.coupons.service.update.TermsAndConditionsUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponKindsAddServiceImpl implements CouponKindsAddService {
    private final CouponKindsRepository couponKindsRepository;
    private final TermsAndConditionsUpdateService termsAndConditionsUpdateService;

    /**
     * 컨트롤단에서 DTO를 통해 이름, 할인율, 최소가격을 받아와서 추가해야함.
     * CouponKindsAddServiceDto의 내부 값 ( 기간 ) 에 따라서 로직이 조금 달라짐
     * 값이 있으면 TermsAndConditionsUpdateService로 가서 기간을 업데이트 하고
     * Null이면 업데이트 하지 않음.
     */
    @Override
    public void add(CouponKindsAddServiceDto couponKindsAddServiceDto) {
        CouponKinds couponKinds = new CouponKinds(couponKindsAddServiceDto.getName(),
                couponKindsAddServiceDto.getDiscountRate(),
                couponKindsAddServiceDto.getMinPrice());

        updateTermsAndConditions(couponKinds, couponKindsAddServiceDto);
        couponKindsRepository.save(couponKinds);
    }

    /**
     * if를 통해서 dto의 값을 검증하고, 적당한 로직을 실행함.
     * 중복으로 사용도 가능하게 만듬.
     * 최종 기간도 있고, 그 기간 전에 쿠폰을 발급 받은 사람은 삭제될 수 있도록 만듬.
     */

    private void updateTermsAndConditions(CouponKinds couponKinds, CouponKindsAddServiceDto couponKindsAddServiceDto) {
        if (couponKindsAddServiceDto.getDeadline() != null) {
            termsAndConditionsUpdateService.update(couponKinds, couponKindsAddServiceDto.getDeadline());
        }

        if (couponKindsAddServiceDto.getPeriod() != null) {
            termsAndConditionsUpdateService.update(couponKinds, couponKindsAddServiceDto.getPeriod());
        }
    }
}

