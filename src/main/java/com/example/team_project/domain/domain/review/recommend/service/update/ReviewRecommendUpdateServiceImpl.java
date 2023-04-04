package com.example.team_project.domain.domain.review.recommend.service.update;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommend;
import com.example.team_project.domain.domain.review.recommend.domain.ReviewRecommendRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.ReviewRecommendStatus;
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

    /**
     * 유저의 고유번호로 유저가 있으면 객체를 가져옴
     * baseReviewValidate 로 리뷰 검증
     * reviewRecommendValidate 로 추천 검증
     * 추천 업데이트 후 저장
     */
    @Override
    public void update(Long userId, Long baseReviewId, String trueOrFalse) {
        userRepository.findById(userId).ifPresent(user -> {
            BaseReview baseReview = baseReviewValidate(baseReviewId);

            ReviewRecommend reviewRecommend = reviewRecommendValidate(user, baseReview);

            reviewRecommend.update(getReviewRecommendName(trueOrFalse));

            reviewRecommendRepository.save(reviewRecommend);
        });
    }

    /**
     * 리뷰의 고유번호로 리뷰가 있으면 객체를 가져옴
     * 없으면 BaseReviewNotFoundException 오류발생
     */
    private BaseReview baseReviewValidate(Long baseReviewId) {
        return baseReviewRepository.findById(baseReviewId)
                .orElseThrow(BaseReviewNotFoundException::new);
    }

    /**
     * 유저와 리뷰 정보를 동시에 가지는 추천 객체를 가져옴
     * 없으면 BaseReviewAndUserNotFoundException 오류발생
     */
    private ReviewRecommend reviewRecommendValidate(User user, BaseReview baseReview) {
        return reviewRecommendRepository.findByUserAndBaseReview(user, baseReview)
                .orElseThrow(BaseReviewAndUserNotFoundException::new);
    }

    /**
     * 리뷰의 추천여부를 Enums 값으로 반환
     */
    private String getReviewRecommendName(String name) {
        return ReviewRecommendStatus.valueOf(name).getName();
    }

}
