package com.example.team_project.domain.domain.review.base.service.add;

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

    @Autowired
    BaseReviewAddServiceTest(BaseReviewAddService baseReviewAddService, Map<String, ReviewJoinKindsService> reviewJoinKindsServiceMap, PostRepository postRepository, UserRepository userRepository, BaseReviewRepository baseReviewRepository) {
        this.baseReviewAddService = baseReviewAddService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.baseReviewRepository = baseReviewRepository;
    }

    @Test
    void 베이스_포스트_리뷰_생성() {
        User user = new User();
        userRepository.save(user);
        Post post = new Post();
        postRepository.save(post);

        ReviewDto reviewDto = new ReviewDto("content", "image", "PostReview", post.getId());
        baseReviewAddService.add(user.getId(), reviewDto);

        BaseReview baseReview = baseReviewRepository.findByUser(user).get();

        assertEquals(baseReview.getContent(), "content");
        assertEquals(baseReview.getImagePath(), "image");
        assertEquals(baseReview.getReviewToKinds().getPostReview().getPost(),post );
        assertNull(baseReview.getReviewToKinds().getProductReview());
    }
}