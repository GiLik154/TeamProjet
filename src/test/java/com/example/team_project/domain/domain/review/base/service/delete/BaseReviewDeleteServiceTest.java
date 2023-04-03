package com.example.team_project.domain.domain.review.base.service.delete;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        User user = new User("user","123");
        userRepository.save(user);

        BaseReview baseReview = new BaseReview();
        baseReviewRepository.save(baseReview);

        baseReviewDeleteService.delete(baseReview.getId(),user.getId(),"123");

        assertEquals(baseReview.getSituation(),"delete");
    }
}