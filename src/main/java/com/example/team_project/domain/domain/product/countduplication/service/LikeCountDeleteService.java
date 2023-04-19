package com.example.team_project.domain.domain.product.countduplication.service;

import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountCheck;
import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeCountDeleteService {

    private final LikeCountCheckService likeCountCheckService;
    private final ProductRepository productRepository;
    private final LikeCountRepository likeCountRepository;
    private final UserRepository userRepository;

    public boolean delete(Long productId, Long userId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);
        Optional<LikeCountCheck> likeCount = likeCountRepository.findByUserIdAndProductId(user, product);
        if (likeCount.isPresent()) {
            likeCountRepository.deleteByProductId(product);
            Product updateProduct = product.get();
            updateProduct.downLikeCount();
            productRepository.save(updateProduct);
            return true;
        } else {
            return false;
        }
    }
}
