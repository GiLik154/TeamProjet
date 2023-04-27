package com.example.team_project.controller.core.coupon.select;

import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.service.issue.UserCouponIssueService;
import com.example.team_project.domain.domain.coupons.usercoupon.service.issue.UserIssueAbleCouponListService;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/my-coupon")
public class UserCouponGetController {
    private final UserCouponRepository userCouponRepository;

    @GetMapping
    public String get() {
        return "thymeleaf/coupon/select";
    }

    @GetMapping("/all")
    @ResponseBody
    public List<UserCoupon> getAllCoupon(@SessionAttribute Long userId) {
        return userCouponRepository.findByUserIdForFetchJoin(userId);
    }

    @GetMapping("/unused")
    @ResponseBody
    public List<UserCoupon> getUnusedCoupon(@SessionAttribute Long userId) {
        return userCouponRepository.findByUserIdAndStatusUnused(userId);
    }

    @GetMapping("/used")
    @ResponseBody
    public List<UserCoupon> getUsedCoupon(@SessionAttribute Long userId) {
        return userCouponRepository.findByUserIdAndStatusUsed(userId);
    }

    @GetMapping("/expired")
    @ResponseBody
    public List<UserCoupon> getExpiredCoupon(@SessionAttribute Long userId) {
        return userCouponRepository.findByUserIdAndStatusExpired(userId);
    }
}