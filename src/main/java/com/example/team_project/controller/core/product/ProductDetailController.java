package com.example.team_project.controller.core.product;


import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountCheck;
import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("product/detail")
public class ProductDetailController {

    private final ProductRepository productRepository;
    private final LikeCountRepository likeCountRepository;
    private final UserRepository userRepository;
    private final BaseReviewRepository baseReviewRepository;

    @GetMapping("{productId}")
    public String detail(@PathVariable Long productId, Model model, @SessionAttribute("userId") Long userId,
                         int page) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);

        int limitPage = 5;
        Pageable pageable = PageRequest.of(page - 1, limitPage, Sort.Direction.DESC, "id");
        Page<BaseReview> productReview = baseReviewRepository.findByReviewToKinds_ProductReview_ProductIdAndSituation(productId,
                "create",
                pageable
                );

        int pageBlock = (int)Math.round((double)productReview.getTotalPages()/limitPage);
        int startPage = (pageBlock-1) * limitPage;
        int EndPage = (pageBlock) * limitPage;

        //좋아요 누르면 빨간하트 아니면 검은하트!
        Optional<LikeCountCheck> likeCount = likeCountRepository.findByUserIdAndProductId(user, product);
        boolean isLiked = likeCount.isPresent();

        model.addAttribute("productDetail", product);
        model.addAttribute("productReviewList", productReview);
        model.addAttribute("isLiked", isLiked);

        return "thymeleaf/product/productUserDetail";
    }

}
