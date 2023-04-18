package com.example.team_project.controller.core.review.product;

import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import com.example.team_project.domain.domain.review.base.service.update.BaseReviewUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/review/update")
public class ProductReviewUpdateController {

    private final BaseReviewUpdateService baseReviewUpdateService;
    private final BaseReviewRepository baseReviewRepository;

    @GetMapping("")
    public String get(@RequestParam("reviewId") Long baseReviewId,
                      Model model){

        baseReviewRepository.findById(baseReviewId).ifPresent(baseReview -> {
            model.addAttribute("baseReview",baseReview);
        });
        return "thymeleaf/product/review-update";
    }

    @PostMapping("")
    public String post(@RequestParam("baseReviewId") Long baseReviewId,
                       @SessionAttribute("userId") Long userId,
                       ReviewDto dto,
                       MultipartFile file){

        baseReviewUpdateService.update(baseReviewId,userId,dto,file);
        return "redirect:/product/detail/"+ dto.getKindsId();
    }
}
