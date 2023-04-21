package com.example.team_project.controller.core.review.product;

import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.service.delete.BaseReviewDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/review/delete")
public class ProductReviewDeleteController {

    private final BaseReviewDeleteService baseReviewDeleteService;
    private final BaseReviewRepository baseReviewRepository;

    @GetMapping("")
    public String get(@RequestParam("reviewId") Long baseReviewId,
                      Model model) {
        model.addAttribute("baseReviewId", baseReviewId);
        return "thymeleaf/review/validate-product";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam("baseReviewId") Long baseReviewId,
                       @RequestParam("password") String password) {
        AtomicReference<Long> productId = new AtomicReference<>(0L);

        baseReviewDeleteService.delete(baseReviewId, userId);
        baseReviewRepository.findById(baseReviewId).ifPresent(baseReview -> {
            productId.set(baseReview.getReviewToKinds().getProductReview().getProduct().getId());
        });

        return "redirect:/product/detail/" + productId.get();
    }
}
