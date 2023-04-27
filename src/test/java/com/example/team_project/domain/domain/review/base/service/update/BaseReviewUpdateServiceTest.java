package com.example.team_project.domain.domain.review.base.service.update;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import com.example.team_project.domain.domain.review.kinds.post.domain.PostReview;
import com.example.team_project.domain.domain.review.kinds.product.domain.ProductReview;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PostCategoryStatus;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BaseReviewUpdateServiceTest {

    private final BaseReviewUpdateService baseReviewUpdateService;
    private final BaseReviewRepository baseReviewRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    BaseReviewUpdateServiceTest(BaseReviewUpdateService baseReviewUpdateService, BaseReviewRepository baseReviewRepository, UserRepository userRepository, PostRepository postRepository, ProductRepository productRepository, SellerRepository sellerRepository, ProductCategoryRepository productCategoryRepository) {
        this.baseReviewUpdateService = baseReviewUpdateService;
        this.baseReviewRepository = baseReviewRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Test
    void 베이스_게시물_리뷰_업데이트_정상작동() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("testId1", "testPw1", "testNane", "testEmail", "testNumber");
        userRepository.save(user);

        PostCategoryStatus status = PostCategoryStatus.valueOf("PRODUCT_INQUIRY");

        Post post = new Post("postTitle", "postContent", "postTime", user, new PostCategory(status));
        postRepository.save(post);

        BaseReview baseReview = new BaseReview(user, "reviewTitle", "reviewContent", "reviewTime", new ReviewToKinds(new PostReview()));
        baseReviewRepository.save(baseReview);

        ReviewDto reviewDto = new ReviewDto("reviewTitle2", "reviewContent2", "PostReview", post.getId());

        baseReviewUpdateService.update(baseReview.getId(), user.getId(), reviewDto, file);
        BaseReview testBaseReview = baseReviewRepository.findById(baseReview.getId()).get();

        assertEquals(user, testBaseReview.getUser());
        assertNull(testBaseReview.getReviewToKinds().getProductReview());
        assertEquals("reviewTitle2", testBaseReview.getTitle());
        assertEquals("reviewContent2", testBaseReview.getContent());
        assertNotNull(testBaseReview.getImagePath());
    }

    @Test
    void 베이스_상품_리뷰_업데이트_정상작동() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("testId1", "testPw1", "testNane", "testEmail", "testNumber");
        userRepository.save(user);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("productTest", null, "imageTest", 1, 10, null);
        productRepository.save(product);

        BaseReview baseReview = new BaseReview(user, "title", "content", "time", new ReviewToKinds(new ProductReview()));
        baseReviewRepository.save(baseReview);

        ReviewDto reviewDto = new ReviewDto("title2", "content2", "ProductReview", product.getId());

        baseReviewUpdateService.update(baseReview.getId(), user.getId(), reviewDto, file);
        BaseReview testBaseReview = baseReviewRepository.findById(baseReview.getId()).get();

        assertEquals(user, testBaseReview.getUser());
        assertNull(testBaseReview.getReviewToKinds().getPostReview());
        assertEquals("title2", testBaseReview.getTitle());
        assertEquals("content2", testBaseReview.getContent());
        assertNotNull(testBaseReview.getImagePath());
    }

    @Test
    void 베이스_리뷰_업데이트_유저다름() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("testId1", "testPw1", "testNane", "testEmail1", "testNumber1");
        userRepository.save(user);
        User user2 = new User("testId2", "testPw2", "testNane", "testEmail2", "testNumber2");
        userRepository.save(user2);

        PostCategoryStatus status = PostCategoryStatus.valueOf("PRODUCT_INQUIRY");

        Post post = new Post("postTitle", "postContent", "postTime", user, new PostCategory(status));
        postRepository.save(post);

        BaseReview baseReview = new BaseReview(user, "title", "content", "time", new ReviewToKinds(new PostReview()));
        baseReviewRepository.save(baseReview);

        ReviewDto reviewDto = new ReviewDto("title", "content2", "PostReview", post.getId());

        baseReviewUpdateService.update(baseReview.getId(), user2.getId(), reviewDto, file);
        BaseReview testBaseReview = baseReviewRepository.findById(baseReview.getId()).get();

        assertNotEquals(user2, testBaseReview.getUser());
        assertNull(testBaseReview.getReviewToKinds().getProductReview());
        assertNotEquals("content2", testBaseReview.getContent());
        assertNotEquals("image2", testBaseReview.getImagePath());
        assertNull(testBaseReview.getImagePath());
    }
}