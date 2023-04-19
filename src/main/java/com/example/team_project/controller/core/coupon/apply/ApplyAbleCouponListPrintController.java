package com.example.team_project.controller.core.coupon.apply;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.service.apply.ApplyCouponListGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public String post(@ModelAttribute("totalPrice") int totalPrice,
                       @RequestParam Long coupon, Model model) {

        System.out.println(coupon);

//        model.addAttribute("price", totalPrice * (100 - Integer.parseInt(discountRate)) / 100);

        return "thymeleaf/coupon/apply-coupon-list";
    }
}