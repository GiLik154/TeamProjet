package com.example.team_project.domain.domain.coupons.service.add.couponkinds;

import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.service.add.couponkinds.dto.CouponKindsAddServiceDto;
import com.example.team_project.exception.InvalidCouponInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponKindsAddServiceImplTest {
    private final CouponKindsAddService couponKindsAddService;
    private final CouponKindsRepository couponKindsRepository;

    @Autowired
    CouponKindsAddServiceImplTest(CouponKindsAddService couponKindsAddService, CouponKindsRepository couponKindsRepository) {
        this.couponKindsAddService = couponKindsAddService;
        this.couponKindsRepository = couponKindsRepository;
    }

    @Test
    void 쿠폰_종류_생성_정상작동_할인율1() {
        CouponKindsAddServiceDto dto = new CouponKindsAddServiceDto("testName", 1, 10000);
        couponKindsAddService.add(dto);

        CouponKinds couponKinds = couponKindsRepository.findByName("testName").get();

        assertEquals("testName", couponKinds.getName());
        assertEquals(1, couponKinds.getDiscountRate());
        assertEquals(10000, couponKinds.getMinPrice());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_할인율99() {
        CouponKindsAddServiceDto dto = new CouponKindsAddServiceDto("testName", 99, 10000);
        couponKindsAddService.add(dto);

        CouponKinds couponKinds = couponKindsRepository.findByName("testName").get();

        assertEquals("testName", couponKinds.getName());
        assertEquals(99, couponKinds.getDiscountRate());
        assertEquals(10000, couponKinds.getMinPrice());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_최소구매1() {
        CouponKindsAddServiceDto dto = new CouponKindsAddServiceDto("testName", 50, 1);
        couponKindsAddService.add(dto);

        CouponKinds couponKinds = couponKindsRepository.findByName("testName").get();

        assertEquals("testName", couponKinds.getName());
        assertEquals(50, couponKinds.getDiscountRate());
        assertEquals(1, couponKinds.getMinPrice());
    }

    @Test
    void 쿠폰_종류_생성_할인율_0이하() {
        CouponKindsAddServiceDto dto = new CouponKindsAddServiceDto("testName", 0, 100);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponKindsAddService.add(dto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_데드라인_설정() {
        CouponKindsAddServiceDto dto = new CouponKindsAddServiceDto("testName", 1, 10000, LocalDate.now());
        couponKindsAddService.add(dto);

        CouponKinds couponKinds = couponKindsRepository.findByName("testName").get();

        assertEquals("testName", couponKinds.getName());
        assertEquals(1, couponKinds.getDiscountRate());
        assertEquals(10000, couponKinds.getMinPrice());
        assertEquals(LocalDate.now(), couponKinds.getDeadline());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_기간_설정() {
        CouponKindsAddServiceDto dto = new CouponKindsAddServiceDto("testName", 1, 10000, Period.ofDays(7));
        couponKindsAddService.add(dto);

        CouponKinds couponKinds = couponKindsRepository.findByName("testName").get();

        assertEquals("testName", couponKinds.getName());
        assertEquals(1, couponKinds.getDiscountRate());
        assertEquals(10000, couponKinds.getMinPrice());
        assertEquals(Duration.ofDays(7), couponKinds.getPeriod());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_기간And데드라인_설정() {
        CouponKindsAddServiceDto dto = new CouponKindsAddServiceDto("testName", 1, 10000, LocalDate.now(), Period.ofDays(7));
        couponKindsAddService.add(dto);

        CouponKinds couponKinds = couponKindsRepository.findByName("testName").get();

        assertEquals("testName", couponKinds.getName());
        assertEquals(1, couponKinds.getDiscountRate());
        assertEquals(10000, couponKinds.getMinPrice());
        assertEquals(LocalDate.now(), couponKinds.getDeadline());
        assertEquals(Duration.ofDays(7), couponKinds.getPeriod());
    }

    @Test
    void 쿠폰_종류_생성_할인율_100이상() {
        CouponKindsAddServiceDto dto = new CouponKindsAddServiceDto("testName", 100, 100);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponKindsAddService.add(dto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰_종류_생성_최소구매_0미만() {
        CouponKindsAddServiceDto dto = new CouponKindsAddServiceDto("testName", 50, 0);

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponKindsAddService.add(dto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

}