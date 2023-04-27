package com.example.team_project.controller.core.coupon.apply;

import com.example.team_project.controller.advice.ProductCheckExceptionAdvice;
import com.example.team_project.controller.interceptor.UserLoginInterceptor;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategory;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.ProductCategoryStatus;
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

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ApplyAbleCouponListPrintControllerTest {
    private final ApplyAbleCouponListPrintController applyAbleCouponListPrintController;
    private final CouponInCategoryRepository couponInCategoryRepository;
    private final CouponRepository couponRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserLoginInterceptor userLoginInterceptor;
    private final ProductCheckExceptionAdvice productCheckExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    ApplyAbleCouponListPrintControllerTest(ApplyAbleCouponListPrintController applyAbleCouponListPrintController, CouponInCategoryRepository couponInCategoryRepository, CouponRepository couponRepository, ProductCategoryRepository productCategoryRepository, UserRepository userRepository, SellerRepository sellerRepository, ProductRepository productRepository, UserCouponRepository userCouponRepository, UserLoginInterceptor userLoginInterceptor, ProductCheckExceptionAdvice productCheckExceptionAdvice) {
        this.applyAbleCouponListPrintController = applyAbleCouponListPrintController;
        this.couponInCategoryRepository = couponInCategoryRepository;
        this.couponRepository = couponRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.userCouponRepository = userCouponRepository;
        this.userLoginInterceptor = userLoginInterceptor;
        this.productCheckExceptionAdvice = productCheckExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(applyAbleCouponListPrintController)
                .setControllerAdvice(productCheckExceptionAdvice)
                .addInterceptors(userLoginInterceptor)
                .build();
    }

    @Test
    void Get_사용_가능_쿠폰_출력_정상작동() throws Exception {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 5000, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        MockHttpServletRequestBuilder builder = get("/coupon/apply-coupon-list/" + productId + "/" + "5")
                .param("price", String.valueOf(product.getPrice()))
                .session(session);

        List<UserCoupon> userCouponList = userCouponRepository.findByUserId(userId);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/coupon/apply-coupon-list"))
                .andExpect(model().attribute("list", userCouponList));
    }

    @Test
    void Get_사용_가능_쿠폰_출력_물건_고유번호_다름() throws Exception {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 5000, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        MockHttpServletRequestBuilder builder = get("/coupon/apply-coupon-list/" + productId + 1L + "/" + "5")
                .param("price", String.valueOf(product.getPrice()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attributeDoesNotExist("list"))
                .andExpect(model().attribute("errorMessage", "This product does not exist"));
    }
}