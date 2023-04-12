package com.example.team_project.controller.core.review.product;

import com.example.team_project.domain.domain.review.base.service.add.BaseReviewAddService;
import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/review/add")
public class ProductReviewAddController {

    private final BaseReviewAddService baseReviewAddService;

    @PostMapping("")
    public String post(Long userId,
                       ReviewDto dto,
                       MultipartFile file) {

            baseReviewAddService.add(userId,dto,file);
            return "redirect:";
    }
}
