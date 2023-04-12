package com.example.team_project.controller.core.review.post;

import com.example.team_project.domain.domain.review.base.service.add.BaseReviewAddService;
import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/review/add")
public class PostReviewAddController {

    private final BaseReviewAddService baseReviewAddService;
    private final ReviewRecommendRepository reviewRecommendRepository;

    @PostMapping("")
    public String post(Long userId,
                       ReviewDto dto,
                       MultipartFile file) {

        baseReviewAddService.add(151L,dto,file);
            return "redirect:/post/read?postId="+dto.getKindsId();
    }
}
