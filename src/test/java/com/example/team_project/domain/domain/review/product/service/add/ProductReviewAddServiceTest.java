package com.example.team_project.domain.domain.review.product.service.add;

import com.example.team_project.domain.domain.product.domain.Product;
import com.example.team_project.domain.domain.product.domain.ProductRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.kinds.ReviewJoinKindsService;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductReviewAddServiceTest {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Map<String,ReviewJoinKindsService> reviewJoinKindsServiceMap;

    @Autowired
    ProductReviewAddServiceTest(UserRepository userRepository,
                             ProductRepository productRepository,
                                Map<String,ReviewJoinKindsService> reviewJoinKindsServiceMap
    ) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.reviewJoinKindsServiceMap = reviewJoinKindsServiceMap;
    }

    private final String KINDS = "ProductReview";
    @Test
    void 상품_리뷰_객체_반환() {
        User user = new User("name", "password");
        userRepository.save(user);

        Product product = new Product();
        productRepository.save(product);

        ReviewToKinds reviewToKinds = reviewJoinKindsServiceMap.get(KINDS).returnReviewToKindsEntity(product.getId());

        assertEquals(reviewToKinds.getProductReview().getProduct(),product);
    }
}