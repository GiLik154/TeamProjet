package com.example.team_project.controller.core.post;

import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommend;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/read")
public class PostReadController {
    private final PostRepository postRepository;
    private final BaseReviewRepository baseReviewRepository;
    private final ReviewRecommendRepository reviewRecommendRepository;

    @GetMapping("")
    public String get(@RequestParam("postId") Long postId,
                      @SessionAttribute("userId") Long userId,
                      @RequestParam(value = "reviewLink", defaultValue = "false") Boolean reviewLink,
                      Model model) {

        postRepository.findById(postId).ifPresent(post -> {
            List<BaseReview> baseReviewList = baseReviewRepository.findByReviewToKinds_PostReview_PostIdAndSituation(postId, "create");
            List<ReviewRecommend> reviewRecommendList = reviewRecommendRepository.findByBaseReview_ReviewToKinds_PostReview_PostIdAndUser_Id(postId, userId);

            model.addAttribute("reviewLink", reviewLink);
            model.addAttribute("post", post);
            model.addAttribute("postReviewList", baseReviewList);
            model.addAttribute("recommendList", reviewRecommendList.isEmpty() ? null : reviewRecommendList);
        });
        return "thymeleaf/post/read";
    }

    @GetMapping("{reviewId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> recommendInfo(@SessionAttribute("userId") Long userId, @PathVariable Long reviewId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("recommend","Cancel");
        reviewRecommendRepository.findByBaseReview_IdAndUser_Id(reviewId, userId).ifPresent(reviewRecommend -> {
            String recommend = reviewRecommend.getRecommend();
            if (recommend != null) {
                if (recommend.equals("Best")) {
                    resultMap.put("recommend", "Best");
                } else if (recommend.equals("Worst")) {
                    resultMap.put("recommend", "Worst");
                }
            }
        });

        resultMap.put("recommendBestCount", reviewRecommendRepository.countByBaseReview_IdAndRecommend(reviewId,"Best"));
        resultMap.put("recommendWorstCount", reviewRecommendRepository.countByBaseReview_IdAndRecommend(reviewId,"Worst"));
        System.out.println("실행 됌???");
        return ResponseEntity.ok(resultMap);
    }

}
