package com.example.team_project.domain.domain.review.kinds.product.service.add;

import com.example.team_project.domain.domain.product.domain.Product;
import com.example.team_project.domain.domain.product.domain.ProductRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.kinds.ReviewJoinKindsService;
import com.example.team_project.domain.domain.review.kinds.product.domain.ProductReview;
import com.example.team_project.domain.domain.review.kinds.product.domain.ProductReviewRepository;
import com.example.team_project.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("ProductReview")
@Transactional
@RequiredArgsConstructor
public class ProductReviewAddServiceImpl implements ReviewJoinKindsService {
    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;

    @Override
    public ReviewToKinds returnReviewToKindsEntity(Long productId) {
        return new ReviewToKinds(add(productId));
    }

    private ProductReview add(Long productId) {
        ProductReview productReview = new ProductReview(product(productId));
        productReviewRepository.save(productReview);
        return productReview;
    }

    private Product product(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }
}
