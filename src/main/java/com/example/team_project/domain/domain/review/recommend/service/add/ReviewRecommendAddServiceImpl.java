package com.example.team_project.domain.domain.review.recommend.service.add;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommend;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import com.example.team_project.domain.domain.review.recommend.service.update.ReviewRecommendUpdateService;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.ReviewRecommendStatus;
import com.example.team_project.exception.BaseReviewAndUserNotFoundException;
import com.example.team_project.exception.BaseReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewRecommendAddServiceImpl implements ReviewRecommendAddService {
    private final ReviewRecommendRepository reviewRecommendRepository;
    private final UserRepository userRepository;
    private final BaseReviewRepository baseReviewRepository;

    @Override
    public boolean add(Long userId, Long baseReviewId, String trueOrFalse) {
        AtomicBoolean result = new AtomicBoolean(false);

        userRepository.findById(userId).ifPresent(user -> {

            BaseReview baseReview = baseReviewValidate(baseReviewId);

            if (!duplication(user, baseReview)) {

                ReviewRecommend reviewRecommend = new ReviewRecommend(user,
                        baseReview,
                        getReviewRecommendName(trueOrFalse)
                );

                reviewRecommendRepository.save(reviewRecommend);

                result.set(true);
            }
        });

        return result.get();
    }

    /**
     * 리뷰의 고유번호로 리뷰가 있으면 객체를 가져옴
     * 없으면 BaseReviewNotFoundException 오류발생
     */
    private BaseReview baseReviewValidate(Long baseReviewId) {
        return baseReviewRepository.findById(baseReviewId).orElseThrow(BaseReviewNotFoundException::new);
    }

    /**
     * 리뷰의 추천여부를 Enums 값으로 반환
     */
    private String getReviewRecommendName(String name){
        return ReviewRecommendStatus.valueOf(name).getName();
    }

    /**
     * 유저정보와 리뷰정보를 동시에 가지고있는 추천객체가 있으면 true 반환
     */
    private boolean duplication(User user, BaseReview baseReview) {
        if (reviewRecommendRepository.existsByUserAndBaseReview(user, baseReview)) {
            return true;
        }
        return false;
    }
}
