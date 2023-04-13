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

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/read")
public class PostReadController {
    private final PostRepository postRepository;
    private final BaseReviewRepository baseReviewRepository;
    private final ReviewRecommendRepository reviewRecommendRepository;

    @GetMapping("")
    public String get(@RequestParam("postId")Long postId,
                      @SessionAttribute("userId") Long userId,
                      @RequestParam(value = "reviewLink", defaultValue = "false") Boolean reviewLink,
                      Model model){

        postRepository.findById(postId).ifPresent(post -> {
            List<BaseReview>baseReviewList = baseReviewRepository.findByReviewToKinds_PostReview_PostIdAndSituation(postId,"create");
            List<ReviewRecommend>reviewRecommendList = reviewRecommendRepository.findByBaseReview_ReviewToKinds_PostReview_PostIdAndUser_Id(postId,userId);

            model.addAttribute("reviewLink",reviewLink);
            model.addAttribute("post",post);
            model.addAttribute("postReviewList",baseReviewList);
            model.addAttribute("recommendList",reviewRecommendList.isEmpty() ? null : reviewRecommendList);
        });
        return "thymeleaf/post/read";
    }

    @GetMapping("/recommend")
    @ResponseBody
    public String recommendInfo(@RequestParam Long baseReviewId,@SessionAttribute("userId") Long userId) {
        System.out.println("sdfdsfsdf"+baseReviewId);
        // Handle post update logic here
            AtomicReference<String> result = new AtomicReference<>("Cancel");
        reviewRecommendRepository.findByBaseReview_IdAndUser_Id(baseReviewId,userId).ifPresent(reviewRecommend -> {
            if(reviewRecommend.getRecommend().equals("Best")){
                result.set("Best");
            }else if (reviewRecommend.getRecommend().equals("Worst")){
                result.set("Worst");
            }else{
                result.set("Cancel");
            }
        });

        System.out.println(result.get() + "ASdfasdfadsfas");

        return result.get();
    }
}
