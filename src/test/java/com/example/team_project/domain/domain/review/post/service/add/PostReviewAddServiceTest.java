package com.example.team_project.domain.domain.review.post.service.add;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.kinds.ReviewJoinKindsService;
import com.example.team_project.domain.domain.review.kinds.post.domain.PostReviewRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PostCategoryStatus;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostReviewAddServiceTest {
    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PostRepository postRepository;
    private final Map<String,ReviewJoinKindsService> reviewJoinKindsServiceMap;

    @Autowired
    PostReviewAddServiceTest(UserRepository userRepository,
                             PostCategoryRepository postCategoryRepository,
                             PostRepository postRepository,
                             Map<String,ReviewJoinKindsService> reviewJoinKindsServiceMap
    ) {
        this.userRepository = userRepository;
        this.postCategoryRepository = postCategoryRepository;
        this.postRepository = postRepository;
        this.reviewJoinKindsServiceMap = reviewJoinKindsServiceMap;
    }

    private final String KINDS = "PostReview";
    @Test
    void 게시물_리뷰_객체_반환() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        PostCategory postCategory = new PostCategory(PostCategoryStatus.PRODUCT_INQUIRY);
        postCategoryRepository.save(postCategory);

        Post post = new Post("testTitle","postContent", "postTime", user, postCategory);
        postRepository.save(post);

        ReviewToKinds reviewToKinds = reviewJoinKindsServiceMap.get(KINDS).returnReviewToKindsEntity(post.getId());

        assertEquals(post,reviewToKinds.getPostReview().getPost());
    }
}