package com.example.team_project.controller.core.product;


import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountCheck;
import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/detail")
public class ProductDetailController {

    private final ProductRepository productRepository;
    private final LikeCountRepository likeCountRepository;
    private final UserRepository userRepository;

    @GetMapping("{productId}")
    public String detail(@PathVariable Long productId, Model model, @SessionAttribute("userId") Long userId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);
        model.addAttribute("productDetail", product);

        //좋아요 누르면 빨간하트 아니면 검은하트!
        Optional<LikeCountCheck> likeCount = likeCountRepository.findByUserIdAndProductId(user, product);
        boolean isLiked = likeCount.isPresent();

        model.addAttribute("isLiked", isLiked);

        return "thymeleaf/product/productUserDetail";
    }
}
