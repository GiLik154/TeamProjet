package com.example.team_project.domain.domain.coupons.coupon.service.update;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.coupon.service.update.CouponUpdateService;
import com.example.team_project.domain.domain.coupons.coupon.service.update.dto.CouponUpdateServiceDto;
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
class CouponUpdateServiceImplTest {
    private final CouponUpdateService couponKindsDeleteService;
    private final CouponRepository couponRepository;

    @Autowired
    CouponUpdateServiceImplTest(CouponUpdateService couponKindsDeleteService, CouponRepository couponRepository) {
        this.couponKindsDeleteService = couponKindsDeleteService;
        this.couponRepository = couponRepository;
    }

    @Test
    void 쿠폰종류_정보_수정_할인율1() {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        CouponUpdateServiceDto couponUpdateServiceDto = new CouponUpdateServiceDto(1, 9999, 1);

        couponKindsDeleteService.update("testName", couponUpdateServiceDto);

        Coupon updateEntity = couponRepository.findByName("testName").get();

        assertEquals("testName", updateEntity.getName());
        assertEquals(1, updateEntity.getDiscountRate());
        assertEquals(9999, updateEntity.getMinPrice());
    }

    @Test
    void 쿠폰종류_정보_수정_할인율99() {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        CouponUpdateServiceDto couponUpdateServiceDto = new CouponUpdateServiceDto(99, 9999, 1);

        couponKindsDeleteService.update("testName", couponUpdateServiceDto);

        Coupon updateEntity = couponRepository.findByName("testName").get();

        assertEquals("testName", updateEntity.getName());
        assertEquals(99, updateEntity.getDiscountRate());
        assertEquals(9999, updateEntity.getMinPrice());
    }

    @Test
    void 쿠폰종류_정보_수정_최소구매_1() {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        CouponUpdateServiceDto couponUpdateServiceDto = new CouponUpdateServiceDto(50, 1, 1);

        couponKindsDeleteService.update("testName", couponUpdateServiceDto);

        Coupon updateEntity = couponRepository.findByName("testName").get();

        assertEquals("testName", updateEntity.getName());
        assertEquals(50, updateEntity.getDiscountRate());
        assertEquals(1, updateEntity.getMinPrice());
    }

    @Test
    void 쿠폰종류_정보_수정_할인율_0미만() {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        CouponUpdateServiceDto couponUpdateServiceDto = new CouponUpdateServiceDto(0, 9999, 1);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponKindsDeleteService.update("testName", couponUpdateServiceDto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰종류_정보_수정_할인율_100이상() {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        CouponUpdateServiceDto couponUpdateServiceDto = new CouponUpdateServiceDto(100, 9999, 1);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponKindsDeleteService.update("testName", couponUpdateServiceDto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰종류_정보_수정_최소구매_0이하() {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        CouponUpdateServiceDto couponUpdateServiceDto = new CouponUpdateServiceDto(50, 0, 1);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponKindsDeleteService.update("testName", couponUpdateServiceDto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }
}