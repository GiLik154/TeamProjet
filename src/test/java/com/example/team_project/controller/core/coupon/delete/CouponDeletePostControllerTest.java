package com.example.team_project.controller.core.coupon.delete;

import com.example.team_project.controller.core.coupon.delete.CouponDeleteController;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponDeletePostControllerTest {
    private final CouponDeleteController couponDeleteController;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    CouponDeletePostControllerTest(CouponDeleteController couponDeleteController, CouponRepository couponRepository, UserRepository userRepository) {
        this.couponDeleteController = couponDeleteController;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(couponDeleteController)
                .build();
    }

    @Test
    void Post_쿠폰_삭제_정상출력() throws Exception {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        MockHttpServletRequestBuilder builder = post("/coupon/delete/" + coupon.getName())
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk());

        Optional<Coupon> testCouponOptional = couponRepository.findByName(coupon.getName());

        assertTrue(testCouponOptional.isEmpty());
    }
}