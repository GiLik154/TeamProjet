package com.example.team_project.domain.domain.review.recommend.service.add;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommend;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.BaseReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewRecommendAddServiceImpl implements ReviewRecommendAddService {
    private final ReviewRecommendRepository reviewRecommendRepository;
    private final UserRepository userRepository;
    private final BaseReviewRepository baseReviewRepository;

    @Override
    public void add(Long userId, Long baseReviewId, String trueOrFalse) {
        userRepository.findById(userId).ifPresent(user -> {
            BaseReview baseReview = baseReviewValidate(baseReviewId);
            ReviewRecommend reviewRecommend = new ReviewRecommend(user,baseReview,trueOrFalse(trueOrFalse));

            reviewRecommendRepository.save(reviewRecommend);
        });
    }

    private BaseReview baseReviewValidate(Long baseReviewId) {
        return baseReviewRepository.findById(baseReviewId).orElseThrow(BaseReviewNotFoundException::new);
    }

    private Boolean trueOrFalse(String trueOrFalse) {
        if (trueOrFalse.equals("true")) {
            return true;
        }
        return false;
    }
}
