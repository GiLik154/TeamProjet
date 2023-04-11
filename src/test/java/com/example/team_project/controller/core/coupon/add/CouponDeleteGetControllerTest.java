package com.example.team_project.controller.core.coupon.add;

import com.example.team_project.controller.core.coupon.delete.CouponDeleteController;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponDeleteGetControllerTest {
    private final CouponDeleteController couponDeleteController;
    private final CouponRepository couponRepository;
    private MockMvc mockMvc;

    @Autowired
    CouponDeleteGetControllerTest(CouponDeleteController couponDeleteController, CouponRepository couponRepository) {
        this.couponDeleteController = couponDeleteController;
        this.couponRepository = couponRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(couponDeleteController)
                .build();
    }

    @Test
    void Get_쿠폰_삭제_정상출력() throws Exception {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        MockHttpServletRequestBuilder builder = get("/coupon/delete");

        List<Coupon> couponList = couponRepository.findAll();

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("coupon_list", couponList));
    }
}