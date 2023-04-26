package com.example.team_project.controller.core.coupon.apply;

import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.service.apply.ApplyCouponListGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.SecureRandom;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/apply-coupon-list")
public class ApplyAbleCouponListPrintController {
    private final ApplyCouponListGetService applyCouponListGetService;
    private final UserCouponRepository userCouponRepository;

    @GetMapping("/{productId}/{quantity}")
    public String get(@SessionAttribute Long userId,
                      @PathVariable Long productId,
                      @PathVariable int quantity,
                      @RequestParam int price, Model model) {

        model.addAttribute("list", applyCouponListGetService.getList(userId, productId, price * quantity));

        return "thymeleaf/coupon/apply-coupon-list";
    }

    @PostMapping("/{price}/{quantity}")
    public String post(@PathVariable int price,
                       @PathVariable int quantity,
                       @RequestParam Long userCouponId,
                       HttpServletResponse response,
                       HttpSession session,
                       Model model) {

        SecureRandom random = new SecureRandom();
        String result = new BigInteger(80, random).toString(32); // 80비트의 랜덤한 BigInteger 값을 32진수 문자열로 변환
        result = result.substring(0, 16); // 토큰을 만든다.

        model.addAttribute("totalPrice", price);

        userCouponRepository.findById(userCouponId).ifPresent(userCoupon -> {
            model.addAttribute("totalPrice", price * quantity * (100 - userCoupon.getCoupon().getDiscountRate()) / 100);
            model.addAttribute("discountRate", userCoupon.getCoupon().getDiscountRate());
        });

        session.setAttribute(result, userCouponId); // 토큰값을 KEY로 해서 우리가 전달하고 싶은 값을 세션으로 전달
        Cookie cookie = new Cookie("couponName", result);
        cookie.setPath("/");
        cookie.setMaxAge(60);
        response.addCookie(cookie);

        return "thymeleaf/coupon/apply-coupon-price";
    }
}