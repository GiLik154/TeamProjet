package com.example.team_project.domain.domain.review.recommend.service.add;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.kinds.post.domain.PostReview;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommend;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRecommendAddServiceTest {
    private final ReviewRecommendAddService reviewRecommendAddService;
    private final UserRepository userRepository;
    private final BaseReviewRepository baseReviewRepository;
    private final ReviewRecommendRepository reviewRecommendRepository;

    @Autowired
    ReviewRecommendAddServiceTest(ReviewRecommendAddService reviewRecommendAddService, UserRepository userRepository, BaseReviewRepository baseReviewRepository, ReviewRecommendRepository reviewRecommendRepository) {
        this.reviewRecommendAddService = reviewRecommendAddService;
        this.userRepository = userRepository;
        this.baseReviewRepository = baseReviewRepository;
        this.reviewRecommendRepository = reviewRecommendRepository;
    }

    @Test
    void 리뷰_추천_정상작동(){
        User user = new User("user","pass");
        userRepository.save(user);

        BaseReview baseReview = new BaseReview(user,"content","time","image",new ReviewToKinds(new PostReview()));
        baseReviewRepository.save(baseReview);

        reviewRecommendAddService.add(user.getId(),baseReview.getId(),"true");

        ReviewRecommend reviewRecommend = reviewRecommendRepository.findByUserAndBaseReview(user,baseReview).get();

        assertTrue(reviewRecommend.getRecommend().booleanValue());
        assertEquals(reviewRecommend.getUser(),user);
        assertEquals(reviewRecommend.getBaseReview(),baseReview);
    }
}