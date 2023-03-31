package com.example.team_project.domain.domain.review.recommend.service.update;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.kinds.post.domain.PostReview;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommend;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import com.example.team_project.domain.domain.review.recommend.service.add.ReviewRecommendAddService;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRecommendUpdateServiceTest {
    private final ReviewRecommendUpdateService reviewRecommendUpdateService;
    private final UserRepository userRepository;
    private final BaseReviewRepository baseReviewRepository;
    private final ReviewRecommendRepository reviewRecommendRepository;

    @Autowired
    ReviewRecommendUpdateServiceTest(ReviewRecommendUpdateService reviewRecommendUpdateService, UserRepository userRepository, BaseReviewRepository baseReviewRepository, ReviewRecommendRepository reviewRecommendRepository) {
        this.reviewRecommendUpdateService = reviewRecommendUpdateService;
        this.userRepository = userRepository;
        this.baseReviewRepository = baseReviewRepository;
        this.reviewRecommendRepository = reviewRecommendRepository;
    }

    @Test
    void 추천_업데이트_정상작동(){
        User user = new User("user","pass");
        userRepository.save(user);

        BaseReview baseReview = new BaseReview(user,"content","time","image",new ReviewToKinds(new PostReview()));
        baseReviewRepository.save(baseReview);

        ReviewRecommend reviewRecommend = new ReviewRecommend(user,baseReview,true);
        reviewRecommendRepository.save(reviewRecommend);

        reviewRecommendUpdateService.update(user.getId(), baseReview.getId(),"false");

        assertFalse(reviewRecommend.getRecommend().booleanValue());
        assertEquals(reviewRecommend.getUser(),user);
        assertEquals(reviewRecommend.getBaseReview(),baseReview);
    }
}