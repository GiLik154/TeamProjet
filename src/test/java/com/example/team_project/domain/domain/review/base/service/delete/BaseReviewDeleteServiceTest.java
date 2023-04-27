package com.example.team_project.domain.domain.review.base.service.delete;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
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
class BaseReviewDeleteServiceTest {
    private final BaseReviewDeleteService baseReviewDeleteService;
    private final UserRepository userRepository;
    private final BaseReviewRepository baseReviewRepository;

    @Autowired
    BaseReviewDeleteServiceTest(BaseReviewDeleteService baseReviewDeleteService, UserRepository userRepository, BaseReviewRepository baseReviewRepository) {
        this.baseReviewDeleteService = baseReviewDeleteService;
        this.userRepository = userRepository;
        this.baseReviewRepository = baseReviewRepository;
    }

    @Test
    void 삭제_정상작동(){
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        BaseReview baseReview = new BaseReview(user,"content","time","image",new ReviewToKinds());
        baseReviewRepository.save(baseReview);

        baseReviewDeleteService.delete(baseReview.getId(),user.getId());
        BaseReview testBaseReview = baseReviewRepository.findById(baseReview.getId()).get();

        assertEquals("delete",testBaseReview.getSituation());
    }

    @Test
    void 삭제_유저다름(){
        User user = new User("testId1", "testPw1", "testNane", "testEmail1" ,"testNumber1");
        userRepository.save(user);
        User user2 = new User("testId2", "testPw2", "testNane", "testEmail2", "testNumber2");
        userRepository.save(user2);

        BaseReview baseReview = new BaseReview(user,"content","time","image",new ReviewToKinds());
        baseReviewRepository.save(baseReview);

        baseReviewDeleteService.delete(baseReview.getId(),user2.getId());
        BaseReview testBaseReview = baseReviewRepository.findById(baseReview.getId()).get();

        assertNotEquals("delete",testBaseReview.getSituation());
    }
}