package com.example.team_project.domain.domain.review.base.service.add;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
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
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BaseReviewAddServiceTest {
    private final BaseReviewAddService baseReviewAddService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BaseReviewRepository  baseReviewRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Autowired
    BaseReviewAddServiceTest(BaseReviewAddService baseReviewAddService, Map<String, ReviewJoinKindsService> reviewJoinKindsServiceMap, PostRepository postRepository, UserRepository userRepository, BaseReviewRepository baseReviewRepository, PostCategoryRepository postCategoryRepository) {
        this.baseReviewAddService = baseReviewAddService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.baseReviewRepository = baseReviewRepository;
        this.postCategoryRepository = postCategoryRepository;
    }

    @Test
    void 베이스_포스트_리뷰_생성() {
        User user = new User("name","pass");
        userRepository.save(user);

        PostCategory postCategory = new PostCategory("category");
        postCategoryRepository.save(postCategory);

        Post post = new Post("postContent","time",user,postCategory);
        postRepository.save(post);

        ReviewDto reviewDto = new ReviewDto("reviewContent", "reviewImage", "PostReview", post.getId());
        baseReviewAddService.add(user.getId(), reviewDto);

        BaseReview baseReview = baseReviewRepository.findByUserId(user.getId()).get();

        assertEquals(baseReview.getContent(), "reviewContent");
        assertEquals(baseReview.getImagePath(), "reviewImage");
        assertEquals(baseReview.getReviewToKinds().getPostReview().getPost().getContent(),"postContent");
//        assertNull(baseReview.getReviewToKinds().getProductReview());
    }
}