package com.example.team_project.controller.review.product;

import com.example.team_project.domain.domain.review.base.service.delete.BaseReviewDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/review/delete")
public class ProductReviewDeleteController {

    private final BaseReviewDeleteService baseReviewDeleteService;

    @GetMapping("")
    public String get(){
        return "thymeleaf/validate-password";
    }

    @PostMapping("")
    public String post(Long baseReviewId,
                       Long userId){

        baseReviewDeleteService.delete(baseReviewId,userId);
        return "redirect:";
    }
}
