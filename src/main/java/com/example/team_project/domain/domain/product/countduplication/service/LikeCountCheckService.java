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
public class LikeCountCheckService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final LikeCountRepository likeCountRepository;

    public boolean countCheck(Long userId, Long productId) {

        Optional<User> user = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);

        //db에서 userid productid 와 맞는 값 검색
        Optional<LikeCountCheck> countCheckOptional = likeCountRepository.findByUserIdAndProductId(user, product);

        if (countCheckOptional.isPresent()) {
            //값이존재하면 return false
            return false;
        } else {
            LikeCountCheck likeCountCheck = LikeCountCheck.builder().userId(user.get()).productId(product.get()).build();
            likeCountRepository.save(likeCountCheck);
            Product updateProduct = product.get();
            updateProduct.updateLikeCount();
            productRepository.save(updateProduct);
            return true;
        }
    }
}
