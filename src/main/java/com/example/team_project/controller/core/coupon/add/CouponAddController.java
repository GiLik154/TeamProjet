package com.example.team_project.controller.core.coupon.add;

import com.example.team_project.controller.core.coupon.add.dto.CouponAddControlDto;
import com.example.team_project.domain.domain.coupons.coupon.service.add.CouponAddService;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/add")
public class CouponAddController {
    private final CouponAddService couponAddService;
    private final ProductCategoryRepository productCategoryRepository;

    @GetMapping
    public String get(Model model) {
        model.addAttribute("categoryList", productCategoryRepository.findAll());
        return "thymeleaf/coupon/add";
    }

    @PostMapping
    public String post(@SessionAttribute Long userId, @Valid CouponAddControlDto couponAddControlDto) {
        couponAddService.add(userId, couponAddControlDto.convertServiceDto());
        return "redirect:/main";
    }
}
