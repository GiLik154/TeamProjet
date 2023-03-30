package com.example.team_project.domain.domain.coupons.service.add.couponkinds;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.service.add.couponkinds.dto.CouponKindsAddServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponKindsAddServiceImpl implements CouponKindsAddService {
    private final CouponKindsRepository couponKindsRepository;

    /**
     * 컨트롤단에서 DTO를 통해 이름, 할인율, 최소가격을 받아와서 추가해야함.
     */
    @Override
    public void add(CouponKindsAddServiceDto couponKindsAddServiceDto) {
        CouponKinds couponKinds = new CouponKinds(couponKindsAddServiceDto.getName(),
                couponKindsAddServiceDto.getDiscountRate(),
                couponKindsAddServiceDto.getMinPrice());

        couponKindsRepository.save(couponKinds);
    }
}