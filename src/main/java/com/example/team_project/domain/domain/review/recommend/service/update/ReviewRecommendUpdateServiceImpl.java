package com.example.team_project.domain.domain.review.recommend.service.update;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommend;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.BaseReviewAndUserNotFoundException;
import com.example.team_project.exception.BaseReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewRecommendUpdateServiceImpl implements ReviewRecommendUpdateService {
    private final ReviewRecommendRepository reviewRecommendRepository;
    private final UserRepository userRepository;
    private final BaseReviewRepository baseReviewRepository;

    @Override
    public void update(Long userId, Long baseReviewId, String trueOrFalse) {
        userRepository.findById(userId).ifPresent(user -> {
            BaseReview baseReview = baseReviewValidate(baseReviewId, user);

            ReviewRecommend reviewRecommend = reviewRecommendValidate(user, baseReview);

            reviewRecommend.update(trueOrFalse(trueOrFalse));

            reviewRecommendRepository.save(reviewRecommend);
        });
    }

    private BaseReview baseReviewValidate(Long baseReviewId, User user) {
        return baseReviewRepository.findById(baseReviewId)
                .orElseThrow(BaseReviewNotFoundException::new);
    }

    private ReviewRecommend reviewRecommendValidate(User user, BaseReview baseReview) {
        return reviewRecommendRepository.findByUserAndBaseReview(user, baseReview)
                .orElseThrow(BaseReviewAndUserNotFoundException::new);
    }

    private Boolean trueOrFalse(String trueOrFalse) {
        if (trueOrFalse.equals("true")) {
            return true;
        }
        return false;
    }
}
