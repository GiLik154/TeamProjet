package com.example.team_project.domain.domain.coupons.service.update;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.service.update.dto.CouponKindsUpdateServiceDto;
import com.example.team_project.exception.InvalidCouponInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponKindsUpdateServiceImplTest {
    private final CouponKindsUpdateService couponKindsDeleteService;
    private final CouponKindsRepository couponKindsRepository;

    @Autowired
    CouponKindsUpdateServiceImplTest(CouponKindsUpdateService couponKindsDeleteService, CouponKindsRepository couponKindsRepository) {
        this.couponKindsDeleteService = couponKindsDeleteService;
        this.couponKindsRepository = couponKindsRepository;
    }

    @Test
    void 쿠폰종류_정보_수정_할인율1() {
        CouponKinds couponKinds = new CouponKinds("testName", 50, 10000);
        couponKindsRepository.save(couponKinds);

        CouponKindsUpdateServiceDto couponKindsUpdateServiceDto = new CouponKindsUpdateServiceDto(1, 9999);

        couponKindsDeleteService.update("testName", couponKindsUpdateServiceDto);

        CouponKinds updateEntity = couponKindsRepository.findByName("testName").get();

        assertEquals("testName", updateEntity.getName());
        assertEquals(1, updateEntity.getDiscountRate());
        assertEquals(9999, updateEntity.getMinPrice());
    }

    @Test
    void 쿠폰종류_정보_수정_할인율99() {
        CouponKinds couponKinds = new CouponKinds("testName", 50, 10000);
        couponKindsRepository.save(couponKinds);

        CouponKindsUpdateServiceDto couponKindsUpdateServiceDto = new CouponKindsUpdateServiceDto(99, 9999);

        couponKindsDeleteService.update("testName", couponKindsUpdateServiceDto);

        CouponKinds updateEntity = couponKindsRepository.findByName("testName").get();

        assertEquals("testName", updateEntity.getName());
        assertEquals(99, updateEntity.getDiscountRate());
        assertEquals(9999, updateEntity.getMinPrice());
    }

    @Test
    void 쿠폰종류_정보_수정_최소구매_1() {
        CouponKinds couponKinds = new CouponKinds("testName", 50, 10000);
        couponKindsRepository.save(couponKinds);

        CouponKindsUpdateServiceDto couponKindsUpdateServiceDto = new CouponKindsUpdateServiceDto(50, 1);

        couponKindsDeleteService.update("testName", couponKindsUpdateServiceDto);

        CouponKinds updateEntity = couponKindsRepository.findByName("testName").get();

        assertEquals("testName", updateEntity.getName());
        assertEquals(50, updateEntity.getDiscountRate());
        assertEquals(1, updateEntity.getMinPrice());
    }

    @Test
    void 쿠폰종류_정보_수정_할인율_0미만() {
        CouponKinds couponKinds = new CouponKinds("testName", 50, 10000);
        couponKindsRepository.save(couponKinds);

        CouponKindsUpdateServiceDto couponKindsUpdateServiceDto = new CouponKindsUpdateServiceDto(0, 9999);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponKindsDeleteService.update("testName", couponKindsUpdateServiceDto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰종류_정보_수정_할인율_100이상() {
        CouponKinds couponKinds = new CouponKinds("testName", 50, 10000);
        couponKindsRepository.save(couponKinds);

        CouponKindsUpdateServiceDto couponKindsUpdateServiceDto = new CouponKindsUpdateServiceDto(100, 9999);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponKindsDeleteService.update("testName", couponKindsUpdateServiceDto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰종류_정보_수정_최소구매_0이하() {
        CouponKinds couponKinds = new CouponKinds("testName", 50, 10000);
        couponKindsRepository.save(couponKinds);

        CouponKindsUpdateServiceDto couponKindsUpdateServiceDto = new CouponKindsUpdateServiceDto(50, 0);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponKindsDeleteService.update("testName", couponKindsUpdateServiceDto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }
}