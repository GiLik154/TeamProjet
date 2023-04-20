package com.example.team_project.controller.core.review.post;

import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.service.delete.BaseReviewDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/review/delete")
public class PostReviewDeleteController {

    private final BaseReviewDeleteService baseReviewDeleteService;
    private final BaseReviewRepository baseReviewRepository;

    @GetMapping("")
    public String get(@RequestParam("reviewId") Long baseReviewId,
                      Model model) {
        model.addAttribute("baseReviewId", baseReviewId);
        return "thymeleaf/review/validate-post";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam("baseReviewId") Long baseReviewId,
                       @RequestParam("password") String password) {

        AtomicReference<Long> postId = new AtomicReference<>(0L);

        baseReviewDeleteService.delete(baseReviewId, userId);
        baseReviewRepository.findById(baseReviewId).ifPresent(baseReview -> {
            postId.set(baseReview.getReviewToKinds().getPostReview().getPost().getId());
        });

        return "redirect:/post/read"+ "/" + postId;
    }
}
