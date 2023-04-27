package com.example.team_project.domain.domain.review.product.service.add;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.kinds.ReviewJoinKindsService;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.enums.UserGrade;
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
    private final ProductCategoryRepository productCategoryRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    ProductReviewAddServiceTest(UserRepository userRepository,
                                ProductRepository productRepository,
                                Map<String,ReviewJoinKindsService> reviewJoinKindsServiceMap,
                                ProductCategoryRepository productCategoryRepository, SellerRepository sellerRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.reviewJoinKindsServiceMap = reviewJoinKindsServiceMap;
        this.productCategoryRepository = productCategoryRepository;
        this.sellerRepository = sellerRepository;
    }

    private final String KINDS = "ProductReview";
    @Test
    void 상품_리뷰_객체_반환() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);

        ReviewToKinds reviewToKinds = reviewJoinKindsServiceMap.get(KINDS).returnReviewToKindsEntity(product.getId());

        assertEquals(product,reviewToKinds.getProductReview().getProduct());
    }
}