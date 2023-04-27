package com.example.team_project.domain.domain.review.base.service.add;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PostCategoryStatus;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BaseReviewAddServiceTest {
    private final BaseReviewAddService baseReviewAddService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BaseReviewRepository baseReviewRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    BaseReviewAddServiceTest(BaseReviewAddService baseReviewAddService,
                             PostRepository postRepository,
                             UserRepository userRepository,
                             BaseReviewRepository baseReviewRepository,
                             PostCategoryRepository postCategoryRepository,
                             ProductRepository productRepository, SellerRepository sellerRepository, ProductCategoryRepository productCategoryRepository) {
        this.baseReviewAddService = baseReviewAddService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.baseReviewRepository = baseReviewRepository;
        this.postCategoryRepository = postCategoryRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Test
    void 베이스_게시물_리뷰_생성() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        PostCategory postCategory = new PostCategory(PostCategoryStatus.PRODUCT_INQUIRY);
        postCategoryRepository.save(postCategory);

        Post post = new Post("postTitle", "postContent", "time", user, postCategory);
        postRepository.save(post);

        ReviewDto reviewDto = new ReviewDto("reviewTitle", "reviewContent", "PostReview", post.getId());
        baseReviewAddService.add(user.getId(), reviewDto, file);

        BaseReview baseReview = baseReviewRepository.findByUserId(user.getId()).get();

        assertEquals("reviewTitle", baseReview.getTitle());
        assertEquals("reviewContent", baseReview.getContent());
        assertEquals("postContent", baseReview.getReviewToKinds().getPostReview().getPost().getContent());
        assertNotNull(baseReview.getImagePath());
        assertNull(baseReview.getReviewToKinds().getProductReview());
    }

    @Test
    void 베이스_상품_리뷰_생성() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        productRepository.save(product);

        ReviewDto reviewDto = new ReviewDto("reviewTitle", "reviewContent", "ProductReview", product.getId());
        baseReviewAddService.add(user.getId(), reviewDto, file);

        BaseReview baseReview = baseReviewRepository.findByUserId(user.getId()).get();

        assertEquals("reviewTitle", baseReview.getTitle());
        assertEquals("reviewContent", baseReview.getContent());
        assertEquals("testProduct", baseReview.getReviewToKinds().getProductReview().getProduct().getName());
        assertEquals("testDes", baseReview.getReviewToKinds().getProductReview().getProduct().getDescription());
        assertNotNull(baseReview.getImagePath());
        assertNull(baseReview.getReviewToKinds().getPostReview());
    }

    @Test
    void 베이스_게시물_리뷰_생성_유저_없음() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        PostCategory postCategory = new PostCategory(PostCategoryStatus.PRODUCT_INQUIRY);
        postCategoryRepository.save(postCategory);

        Post post = new Post("postTitle", "postContent", "time", user, postCategory);
        postRepository.save(post);

        ReviewDto reviewDto = new ReviewDto("reviewTitle", "reviewContent", "PostReview", post.getId());

        Exception e = assertThrows(UserNotFoundException.class, () ->
                baseReviewAddService.add(0L, reviewDto, file)
        );
        assertEquals("This user could not be found", e.getMessage());
    }
}