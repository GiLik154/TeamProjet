package com.example.team_project.controller.core.product;


import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountCheck;
import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.kinds.product.domain.ProductReview;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommend;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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

        int limitPost = 5;
        int limitPage = 5;
        Pageable pageable = PageRequest.of(page - 1, limitPost, Sort.Direction.DESC, "id");
        Page<BaseReview> productReview = baseReviewRepository.findByReviewToKinds_ProductReview_ProductIdAndSituation(productId,
                "create",
                pageable
                );

        List<ReviewRecommend>reviewRecommendList = reviewRecommendRepository.findByBaseReview_ReviewToKinds_ProductReview_ProductIdAndUser_Id(productId,userId);

        int pageBlock = (int)Math.ceil(((double)productReview.getNumber()+1)/limitPage);
        int startPage = ((pageBlock-1) * limitPage)+1;
        int endPage = (pageBlock) * limitPage;

        //좋아요 누르면 빨간하트 아니면 검은하트!
        Optional<LikeCountCheck> likeCount = likeCountRepository.findByUserIdAndProductId(user, product);
        boolean isLiked = likeCount.isPresent();
        model.addAttribute("isLiked", isLiked);
        //review
        model.addAttribute("limitPage",limitPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("productDetail", product);
        model.addAttribute("productReviewList", productReview);
        model.addAttribute("recommendList",reviewRecommendList.isEmpty() ? null : reviewRecommendList);
        return "thymeleaf/product/productUserDetail";
    }

    @GetMapping("/reviewId/{reviewId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> recommendInfo(@SessionAttribute("userId") Long userId, @PathVariable Long reviewId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("recommend","Cancel");
        reviewRecommendRepository.findByBaseReview_IdAndUser_Id(reviewId, userId).ifPresent(reviewRecommend -> {
            String recommend = reviewRecommend.getRecommend();
            if (recommend != null) {
                if (recommend.equals("Best")) {
                    resultMap.put("recommend", "Best");
                } else if (recommend.equals("Worst")) {
                    resultMap.put("recommend", "Worst");
                }
            }
        });

        resultMap.put("recommendBestCount", reviewRecommendRepository.countByBaseReview_IdAndRecommend(reviewId,"Best"));
        resultMap.put("recommendWorstCount", reviewRecommendRepository.countByBaseReview_IdAndRecommend(reviewId,"Worst"));
        System.out.println("실행 됌???");
        return ResponseEntity.ok(resultMap);
    }

}
