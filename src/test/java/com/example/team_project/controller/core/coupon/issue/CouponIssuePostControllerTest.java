package com.example.team_project.controller.core.coupon.issue;

import com.example.team_project.controller.advice.CouponExceptionAdvice;
import com.example.team_project.controller.advice.UserExceptionAdvice;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.CouponStatus;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponIssuePostControllerTest {
    private final CouponIssueController couponIssueController;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;
    private final CouponExceptionAdvice couponExceptionAdvice;
    private final UserExceptionAdvice userExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    CouponIssuePostControllerTest(CouponIssueController couponIssueController, CouponRepository couponRepository, UserCouponRepository userCouponRepository, UserRepository userRepository, CouponExceptionAdvice couponExceptionAdvice, UserExceptionAdvice userExceptionAdvice) {
        this.couponIssueController = couponIssueController;
        this.couponRepository = couponRepository;
        this.userCouponRepository = userCouponRepository;
        this.userRepository = userRepository;
        this.couponExceptionAdvice = couponExceptionAdvice;
        this.userExceptionAdvice = userExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(couponIssueController)
                .setControllerAdvice(couponExceptionAdvice, userExceptionAdvice)
                .build();
    }

    @Test
    void 쿠폰_발급_정상_작동() throws Exception {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        MockHttpServletRequestBuilder builder = post("/coupon/issue/" + coupon.getName())
                .session(session);


        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));

        List<UserCoupon> list = userCouponRepository.findByUserId(userId);
        UserCoupon userCoupon = list.get(0);

        assertEquals(user, userCoupon.getUser());
        assertEquals(coupon.getName(), userCoupon.getCoupon().getName());
        assertEquals(LocalDate.now().plusDays(7), userCoupon.getExpirationDate());
        assertEquals(CouponStatus.UNUSED, userCoupon.getStatus());
    }

    @Test
    void 쿠폰_발급_발급_횟수_정상() throws Exception {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 2);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        MockHttpServletRequestBuilder builder = post("/coupon/issue/" + coupon.getName())
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));

        List<UserCoupon> list = userCouponRepository.findByUserId(userId);
        UserCoupon testUserCoupon = list.get(1);

        assertEquals(user, testUserCoupon.getUser());
        assertEquals(coupon.getName(), testUserCoupon.getCoupon().getName());
        assertEquals(LocalDate.now().plusDays(7), testUserCoupon.getExpirationDate());
        assertEquals(CouponStatus.UNUSED, testUserCoupon.getStatus());
        assertEquals(2, list.size());
    }

    @Test
    void 쿠폰_발급_기한_만료_쿠폰() throws Exception {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(-7));
        couponRepository.save(coupon);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        MockHttpServletRequestBuilder builder = post("/coupon/issue/" + coupon.getName())
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMessage", "Expired Coupon Exception"));

        List<UserCoupon> list = userCouponRepository.findByUserId(userId);

        assertTrue(list.isEmpty());
    }

    @Test
    void 쿠폰_발급_발급_횟수_초과() throws Exception {
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

        MockHttpServletRequestBuilder builder = post("/coupon/issue/" + coupon.getName())
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));

        List<UserCoupon> list = userCouponRepository.findByUserId(userId);

        assertNotEquals(2, list.size());
    }

    @Test
    void 쿠폰_발급_유저_고유번호_다름() throws Exception {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(7));
        couponRepository.save(coupon);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId + 1L);

        MockHttpServletRequestBuilder builder = post("/coupon/issue/" + coupon.getName())
                .session(session);


        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMessage", "This user could not be found"));

        List<UserCoupon> list = userCouponRepository.findByUserId(userId);

        assertTrue(list.isEmpty());
    }
}