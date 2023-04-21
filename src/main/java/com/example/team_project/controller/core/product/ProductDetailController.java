package com.example.team_project.controller.core.product;


import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountCheck;
import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommend;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/detail")
public class ProductDetailController {

    private final ProductRepository productRepository;
    private final LikeCountRepository likeCountRepository;
    private final UserRepository userRepository;
    private final BaseReviewRepository baseReviewRepository;
    private final ReviewRecommendRepository reviewRecommendRepository;

    @GetMapping("{productId}")
    public String detail(@PathVariable Long productId, Model model, @SessionAttribute("userId") Long userId,
                         @RequestParam(name = "page", defaultValue = "1") int page) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);

        // 페이징 처리
        int limitPost = 5;
        int limitPage = 5;
        Pageable pageable = PageRequest.of(page - 1, limitPost, Sort.Direction.DESC, "id");
        Page<BaseReview> productReview = baseReviewRepository.findByReviewToKinds_ProductReview_ProductIdAndSituation(productId,
                "create",
                pageable
        );

        int pageBlock = (int) Math.ceil(((double) productReview.getNumber() + 1) / limitPage);
        int startPage = ((pageBlock - 1) * limitPage) + 1;
        int endPage = (pageBlock) * limitPage;

        AtomicBoolean isLiked = new AtomicBoolean(false);
        //좋아요 누르면 빨간하트 아니면 검은하트!
        product.ifPresent(product1 -> {
            Optional<LikeCountCheck> likeCount = likeCountRepository.findByUserIdAndProductId(user, product);
            isLiked.set(likeCount.isPresent());
            model.addAttribute("productDetail", product1);
        });

        //  페이징 처리
        model.addAttribute("limitPage", limitPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("productReviewList", productReview);

        model.addAttribute("isLiked", isLiked.get());

        return "thymeleaf/product/productUserDetail";
    }

    /**
     * recommend 창
     */
    @GetMapping(value = "/{productId}/{reviewId}")
    public String recommendInfo(Model model, @SessionAttribute("userId") Long userId, @PathVariable Long reviewId, @PathVariable Long productId) {
        System.out.println("여기 오나???++++" + productId);
        model.addAttribute("recommend", "Cancel");

        reviewRecommendRepository.findByBaseReview_IdAndUser_Id(reviewId, userId).ifPresent(reviewRecommend -> {
            model.addAttribute("recommend", reviewRecommend.getRecommend());
        });

        int bestCount = reviewRecommendRepository.countByBaseReview_IdAndRecommend(reviewId, "Best");
        int worstCount = reviewRecommendRepository.countByBaseReview_IdAndRecommend(reviewId, "Worst");

        model.addAttribute("bestCount", bestCount);
        model.addAttribute("worstCount", worstCount);
        return "thymeleaf/review/product-recommend";
    }
}
