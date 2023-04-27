package com.example.team_project.domain.domain.review.recommend.service.add;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.kinds.post.domain.PostReview;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommend;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        BaseReview baseReview = new BaseReview(user,"content","time","image",new ReviewToKinds(new PostReview()));
        baseReviewRepository.save(baseReview);

        boolean isTrue = reviewRecommendAddService.add(user.getId(),baseReview.getId(),"BEST");

        ReviewRecommend reviewRecommend = reviewRecommendRepository.findByUserAndBaseReview(user,baseReview).get();

        assertTrue(isTrue);
        assertEquals("Best",reviewRecommend.getRecommend());
        assertEquals(user,reviewRecommend.getUser());
        assertEquals(baseReview,reviewRecommend.getBaseReview());
    }

    @Test
    void 리뷰_추천_유저중복(){
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        BaseReview baseReview = new BaseReview(user,"content","time","image",new ReviewToKinds(new PostReview()));
        baseReviewRepository.save(baseReview);

        ReviewRecommend reviewRecommend = new ReviewRecommend(user,baseReview,"Best");
        reviewRecommendRepository.save(reviewRecommend);

        boolean isFalse = reviewRecommendAddService.add(user.getId(),baseReview.getId(),"false");

        ReviewRecommend reviewRecommendTest = reviewRecommendRepository.findByUserAndBaseReview(user,baseReview).get();

        assertFalse(isFalse);
        assertEquals("Best",reviewRecommendTest.getRecommend());
        assertEquals(user,reviewRecommendTest.getUser());
        assertEquals(baseReview,reviewRecommendTest.getBaseReview());
    }

    @Test
    void 리뷰_추천_유저객체_1개확인(){
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        BaseReview baseReview = new BaseReview(user,"content","time","image",new ReviewToKinds(new PostReview()));
        baseReviewRepository.save(baseReview);

        ReviewRecommend reviewRecommend = new ReviewRecommend(user,baseReview,"Best");
        reviewRecommendRepository.save(reviewRecommend);

        boolean isFalse = reviewRecommendAddService.add(user.getId(),baseReview.getId(),"false");

        List<ReviewRecommend>list = reviewRecommendRepository.findByUser(user);

        ReviewRecommend reviewRecommendTest = reviewRecommendRepository.findByUserAndBaseReview(user,baseReview).get();

        assertFalse(isFalse);
        assertEquals(1,list.size());
        assertEquals("Best",reviewRecommendTest.getRecommend());
        assertEquals(user,reviewRecommendTest.getUser());
        assertEquals(baseReview,reviewRecommendTest.getBaseReview());
    }
}