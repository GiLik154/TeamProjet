package com.example.team_project.controller.core.coupon.add;

import com.example.team_project.controller.advice.UserExceptionAdvice;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategory;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponAddPostControllerTest {
    private final CouponAddController couponAddController;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final UserExceptionAdvice userExceptionAdvice;
    private final ProductCategoryRepository productCategoryRepository;
    private final CouponInCategoryRepository couponInCategoryRepository;

    private MockMvc mockMvc;

    @Autowired
    public CouponAddPostControllerTest(CouponAddController couponAddController, UserRepository userRepository, CouponRepository couponRepository, UserExceptionAdvice userExceptionAdvice, ProductCategoryRepository productCategoryRepository, CouponInCategoryRepository couponInCategoryRepository) {
        this.couponAddController = couponAddController;
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
        this.userExceptionAdvice = userExceptionAdvice;
        this.productCategoryRepository = productCategoryRepository;
        this.couponInCategoryRepository = couponInCategoryRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(couponAddController)
                .setControllerAdvice(userExceptionAdvice)
                .build();
    }

    @Test
    void Get_쿠폰_추가_정상작동() throws Exception {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/coupon/add")
                .param("name", "testCoupon")
                .param("discountRate", "5")
                .param("minPrice", "1000")
                .param("maxCouponCount", "1")
                .param("categoryIdList", String.valueOf(productCategory.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/coupon/add"));

        Coupon coupon = couponRepository.findByName("testCoupon").get();
        CouponInCategory couponInCategory = couponInCategoryRepository.findByCouponName("testCoupon").get(0);

        assertEquals("testCoupon", coupon.getName());
        assertEquals(5, coupon.getDiscountRate());
        assertEquals(1000, coupon.getMinPrice());
        assertEquals(coupon, couponInCategory.getCoupon());
        assertEquals(productCategory, couponInCategory.getProductCategory());
    }

    @Test
    void Get_쿠폰_추가_정상작동_유저고유번호_다름() throws Exception {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId() + 1L;

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        MockHttpServletRequestBuilder builder = post("/coupon/add")
                .param("name", "testCoupon")
                .param("discountRate", "5")
                .param("minPrice", "1000")
                .param("maxCouponCount", "1")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMessage", "This user could not be found"));
    }
}

