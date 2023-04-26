package com.example.team_project.controller.core.review.product;

import com.example.team_project.domain.domain.review.base.service.add.BaseReviewAddService;
import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/review/add")
public class ProductReviewAddController {

    private final BaseReviewAddService baseReviewAddService;

    @GetMapping("")
    public String get(@RequestParam("productId")Long productId, Model model){
        model.addAttribute("productId",productId);
        return "thymeleaf/product/product-review";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       ReviewDto dto,
                       MultipartFile file) {

            baseReviewAddService.add(userId,dto,file);
            return "thymeleaf/user/main";
    }
}
