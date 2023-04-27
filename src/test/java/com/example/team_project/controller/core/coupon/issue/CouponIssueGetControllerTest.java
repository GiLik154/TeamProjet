package com.example.team_project.controller.core.coupon.issue;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponIssueGetControllerTest {
    private final CouponIssueController couponIssueController;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    CouponIssueGetControllerTest(CouponIssueController couponIssueController, CouponRepository couponRepository, UserCouponRepository userCouponRepository, UserRepository userRepository) {
        this.couponIssueController = couponIssueController;
        this.couponRepository = couponRepository;
        this.userCouponRepository = userCouponRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(couponIssueController)
                .build();
    }

    @Test
    void 쿠폰_발급_작동() throws Exception {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        MockHttpServletRequestBuilder builder = get("/coupon/issue")
                .session(session);

        List<Coupon> list = couponRepository.findAll();

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/coupon/issue"))
                .andExpect(model().attribute("couponList", list));

        assertFalse(list.isEmpty());
    }

    @Test
    void 쿠폰_발급_가능_쿠폰_없음_기한_만료() throws Exception {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(-7));
        couponRepository.save(coupon);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        MockHttpServletRequestBuilder builder = get("/coupon/issue")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/coupon/issue"));
    }

    @Test
    void 쿠폰_발급_가능_쿠폰_없음_발급한도_초과() throws Exception {
        User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        MockHttpServletRequestBuilder builder = get("/coupon/issue")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/coupon/issue"));
    }
}