package com.example.team_project.controller.core.review.recommend;

import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import com.example.team_project.domain.domain.review.recommend.service.add.ReviewRecommendAddService;
import com.example.team_project.domain.domain.review.recommend.service.update.ReviewRecommendUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review/recommend")
public class ReviewRecommendController {

    private final ReviewRecommendAddService reviewRecommendAddService;
    private final ReviewRecommendUpdateService reviewRecommendUpdateService;

    @PostMapping("/product/{productId}")
    public String postProduct(@SessionAttribute("userId") Long userId,
                              @RequestParam("baseReviewId") Long baseReviewId,
                              @RequestParam("trueOrFalse") String trueOrFalse,
                              @PathVariable Long productId) {
        if (!reviewRecommendAddService.add(userId, baseReviewId, trueOrFalse)) {
            reviewRecommendUpdateService.update(userId, baseReviewId, trueOrFalse);
        }
        System.out.println("리뷰번호===" + baseReviewId);
        return "redirect:/product/detail/" + productId;
    }

    @PostMapping("/post/{postId}") // 수정
    public String postPost(@SessionAttribute("userId") Long userId,
                           @RequestParam("baseReviewId") Long baseReviewId,
                           @RequestParam("trueOrFalse") String trueOrFalse,
                           @PathVariable Long postId) {
        if (!reviewRecommendAddService.add(userId, baseReviewId, trueOrFalse)) {
            reviewRecommendUpdateService.update(userId, baseReviewId, trueOrFalse);
        }
        System.out.println("리뷰번호===" + baseReviewId);
        return "redirect:/post/read/" + postId;
    }
}
