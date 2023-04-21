package com.example.team_project.controller.core.post;

import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommend;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/read")
public class PostReadController {
    private final PostRepository postRepository;
    private final BaseReviewRepository baseReviewRepository;
    private final ReviewRecommendRepository reviewRecommendRepository;

    @GetMapping("{postId}")
    public String get(@PathVariable Long postId,
                      @SessionAttribute("userId") Long userId,
                      @RequestParam(value = "reviewLink", defaultValue = "false") Boolean reviewLink,
                      Model model) {

        postRepository.findById(postId).ifPresent(post -> {
            List<BaseReview> baseReviewList = baseReviewRepository.findByReviewToKinds_PostReview_PostIdAndSituation(postId, "create");

            model.addAttribute("reviewLink", reviewLink);
            model.addAttribute("post", post);
            model.addAttribute("postReviewList", baseReviewList);
        });
        return "thymeleaf/post/read";
    }

    @GetMapping(value = "/{postId}/{reviewId}")
    public String recommendInfo(Model model, @SessionAttribute("userId") Long userId, @PathVariable Long reviewId, @PathVariable Long postId) {
        System.out.println("여기 오나???++++" + postId);
        model.addAttribute("recommend", "Cancel");

        reviewRecommendRepository.findByBaseReview_IdAndUser_Id(reviewId, userId).ifPresent(reviewRecommend -> {
            model.addAttribute("recommend", reviewRecommend.getRecommend());
        });

        int bestCount = reviewRecommendRepository.countByBaseReview_IdAndRecommend(reviewId,"Best");
        int worstCount = reviewRecommendRepository.countByBaseReview_IdAndRecommend(reviewId,"Worst");

        model.addAttribute("bestCount",bestCount);
        model.addAttribute("worstCount",worstCount);
        return "thymeleaf/review/post-recommend";
    }

}
