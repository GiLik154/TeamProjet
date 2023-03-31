package com.example.team_project.domain.domain.review.base.service.update;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import com.example.team_project.domain.domain.review.kinds.post.domain.PostReview;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BaseReviewUpdateServiceTest {

    private final BaseReviewUpdateService baseReviewUpdateService;
    private final BaseReviewRepository baseReviewRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    BaseReviewUpdateServiceTest(BaseReviewUpdateService baseReviewUpdateService, BaseReviewRepository baseReviewRepository, UserRepository userRepository, PostRepository postRepository) {
        this.baseReviewUpdateService = baseReviewUpdateService;
        this.baseReviewRepository = baseReviewRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Test
    void 포스트_리뷰_업데이트_정상작동(){
        User user = new User("userName","userPass");
        userRepository.save(user);

        Post post = new Post("postContent","postTime",user,new PostCategory("category"));
        postRepository.save(post);

        BaseReview baseReview = new BaseReview(user,"content","time","image", new ReviewToKinds(new PostReview()));
        baseReviewRepository.save(baseReview);

        ReviewDto reviewDto = new ReviewDto("content2", "image2", "PostReview", post.getId());

        baseReviewUpdateService.update(baseReview.getId(), user.getId(),reviewDto);

        assertEquals(baseReview.getUser(),user);
        assertEquals(baseReview.getContent(),"content2");
        assertEquals(baseReview.getImagePath(),"image2");
        assertNotEquals(baseReview.getTime(),"time");
    }
}