package com.example.team_project.controller.core.review.recommend;

import com.example.team_project.domain.domain.review.recommend.service.add.ReviewRecommendAddService;
import com.example.team_project.domain.domain.review.recommend.service.update.ReviewRecommendUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review/recommend")
public class ReviewRecommendController {

    private final ReviewRecommendAddService reviewRecommendAddService;
    private final ReviewRecommendUpdateService reviewRecommendUpdateService;
    @PostMapping("")
    public String post(Long userId, @RequestParam("baseReviewId") Long baseReviewId,@RequestParam("trueOrFalse") String trueOrFalse){
        if(!reviewRecommendAddService.add(151L,baseReviewId,trueOrFalse)){
            reviewRecommendUpdateService.update(151L,baseReviewId,trueOrFalse);
        }

        return "redirect:/post/list";
    }
}
