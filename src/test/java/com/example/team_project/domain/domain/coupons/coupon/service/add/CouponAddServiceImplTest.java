package com.example.team_project.domain.domain.coupons.coupon.service.add;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.coupon.service.add.dto.CouponAddServiceDto;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategory;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.InvalidCouponInfo;
import com.example.team_project.exception.UserNotCouponLevelException;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponAddServiceImplTest {
    private final CouponAddService couponAddService;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CouponInCategoryRepository couponInCategoryRepository;

    @Autowired
    CouponAddServiceImplTest(CouponAddService couponAddService, UserRepository userRepository, CouponRepository couponRepository, ProductCategoryRepository productCategoryRepository, CouponInCategoryRepository couponInCategoryRepository) {
        this.couponAddService = couponAddService;
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.couponInCategoryRepository = couponInCategoryRepository;
    }

    @Test
    void 쿠폰_종류_생성_정상작동_할인율1() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 1, 10000, 1, null, null, Collections.emptyList());
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(1, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_할인율99() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 99, 10000, 1, null, null, Collections.emptyList());
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(99, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_최소구매1() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 50, 1, 1, null, null, Collections.emptyList());
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(50, coupon.getDiscountRate());
        assertEquals(1, coupon.getMinPrice());
    }

    @Test
    void 쿠폰_종류_생성_할인율_0이하() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 0, 100, 1, null, null, Collections.emptyList());

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponAddService.add(userId, dto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_데드라인_설정() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 1, 10000, 1, LocalDate.now(), null, Collections.emptyList());
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(1, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
        assertEquals(LocalDate.now(), coupon.getDeadline());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_기간_설정() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 1, 10000, 1, null, Period.ofDays(7), Collections.emptyList());
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(1, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
        assertEquals(Period.ofDays(7), coupon.getPeriod());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_기간And데드라인_설정() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);


        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 1, 10000, 1, LocalDate.now(), Period.ofDays(7), Collections.emptyList());
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();

        assertEquals("testName", coupon.getName());
        assertEquals(1, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
        assertEquals(LocalDate.now(), coupon.getDeadline());
        assertEquals(Period.ofDays(7), coupon.getPeriod());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_카테고리_생성() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        List<Long> prdocutIdList = new ArrayList<>();
        prdocutIdList.add(productCategory.getId());

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 1, 10000, 1, null, null, prdocutIdList);
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();
        CouponInCategory couponInCategory = couponInCategoryRepository.findByCouponName(dto.getName()).get(0);

        assertEquals("testName", coupon.getName());
        assertEquals(1, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
        assertEquals(productCategory, couponInCategory.getProductCategory());
        assertEquals(coupon, couponInCategory.getCoupon());
    }

    @Test
    void 쿠폰_종류_생성_정상작동_카테고리_생성_여러개() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        ProductCategory productCategory1 = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory1);
        ProductCategory productCategory2 = new ProductCategory(ProductCategoryStatus.BOTTOM);
        productCategoryRepository.save(productCategory2);
        ProductCategory productCategory3 = new ProductCategory(ProductCategoryStatus.SHOES);
        productCategoryRepository.save(productCategory3);

        List<Long> prdocutIdList = new ArrayList<>();
        prdocutIdList.add(productCategory1.getId());
        prdocutIdList.add(productCategory2.getId());
        prdocutIdList.add(productCategory3.getId());

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 1, 10000, 1, null, null, prdocutIdList);
        couponAddService.add(user.getId(), dto);

        Coupon coupon = couponRepository.findByName("testName").get();
        List<CouponInCategory> couponInCategoryList = couponInCategoryRepository.findByCouponName(dto.getName());

        assertEquals("testName", coupon.getName());
        assertEquals(1, coupon.getDiscountRate());
        assertEquals(10000, coupon.getMinPrice());
        assertEquals(3, couponInCategoryList.size());
    }


    @Test
    void 쿠폰_종류_생성_할인율_100이상() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();


        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 100, 100, 1, null, null, Collections.emptyList());

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponAddService.add(userId, dto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰_종류_생성_최소구매_0미만() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();


        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 50, 0, 1, null, null, Collections.emptyList());

        InvalidCouponInfo e = assertThrows(InvalidCouponInfo.class, () ->
                couponAddService.add(userId, dto));

        assertEquals("Invalid Coupon Info", e.getMessage());
    }

    @Test
    void 쿠폰_종류_생성_유저_고유키_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 50, 0, 1, null, null, Collections.emptyList());

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () ->
                couponAddService.add(userId + 1L, dto));

        assertEquals("This user could not be found", e.getMessage());
    }

    @Test
    void 쿠폰_종류_생성_유저_등급_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.SILVER);
        userRepository.save(user);
        Long userId = user.getId();

        CouponAddServiceDto dto = new CouponAddServiceDto("testName", 50, 0, 1, null, null, Collections.emptyList());

        UserNotCouponLevelException e = assertThrows(UserNotCouponLevelException.class, () ->
                couponAddService.add(userId, dto));

        assertEquals("The user tried to force a coupon user ID :" + userId, e.getMessage());
    }
}