package com.example.team_project.controller.core.coupon.delete;

import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.coupon.service.delete.CouponDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/delete")
public class CouponDeleteController {
    private final CouponDeleteService couponDeleteService;
    private final CouponRepository couponRepository;

    @GetMapping
    public String get(Model model) {
        model.addAttribute("coupon_list", couponRepository.findAll());
        return "thymeleaf/coupon/delete";
    }

    @PostMapping("/{couponName}")
    public String post(@SessionAttribute Long userId, @PathVariable String couponName) {
        couponDeleteService.delete(userId, couponName);
        return "thymeleaf/coupon/delete";
    }
}
