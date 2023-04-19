package com.example.team_project.controller.core.coupon.apply;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.service.apply.ApplyCouponListGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/apply-coupon-list")
public class ApplyAbleCouponListPrintController {
    private final ApplyCouponListGetService applyCouponListGetService;
    private final CouponRepository couponRepository;

    @GetMapping("/{productId}/{quantity}")
    public String get(@SessionAttribute Long userId,
                      @PathVariable Long productId,
                      @PathVariable int quantity,
                      @RequestParam int price, Model model) {

        model.addAttribute("list", applyCouponListGetService.getList(userId, productId, price * quantity));
        model.addAttribute("totalPrice", price * quantity);

        return "thymeleaf/coupon/apply-coupon-list";
    }

    @PostMapping("/{price}/{quantity}")
    public String post(@PathVariable int price,
                       @PathVariable int quantity,
                       @RequestParam String couponName,
                       HttpServletResponse response,
                       Model model) {

        couponRepository.findByName(couponName).ifPresent(coupon ->
                model.addAttribute("totalPrice", price * quantity * (100 - coupon.getDiscountRate()) / 100)
        );

        Cookie cookie = new Cookie("couponName", couponName);
        cookie.setPath("/");
        cookie.setMaxAge(60);
        response.addCookie(cookie);

        return "thymeleaf/coupon/apply-coupon-price";
    }
}