package com.example.team_project.controller.core.review.post;

import com.example.team_project.domain.domain.review.base.service.delete.BaseReviewDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/review/delete")
public class PostReviewDeleteController {

    private final BaseReviewDeleteService baseReviewDeleteService;

    @GetMapping("")
    public String get(@RequestParam("baseReviewId") Long baseReviewId,
                      @RequestParam("postId") Long postId,
                      Model model) {
        model.addAttribute("baseReviewId", baseReviewId);
        model.addAttribute("postId", postId);
        return "thymeleaf/review/validate-post";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam("baseReviewId") Long baseReviewId,
                       @RequestParam("postId") Long postId,
                       @RequestParam("password") String password) {

        baseReviewDeleteService.delete(baseReviewId, userId);
        return "redirect:/post/read?postId=" + postId;
    }
}
