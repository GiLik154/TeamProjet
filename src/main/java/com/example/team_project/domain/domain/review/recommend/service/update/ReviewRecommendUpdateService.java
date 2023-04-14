package com.example.team_project.domain.domain.review.recommend.service.update;

public interface ReviewRecommendUpdateService {
    /**
     * 유저의 고유번호로 유저가 있으면 객체를 가져옴
     * baseReviewValidate 로 리뷰 검증
     * reviewRecommendValidate 로 추천 검증
     * 추천 업데이트 후 저장
     * @param userId 유저의 고유번호
     * @param baseReviewId 리뷰의 고유번호
     * @param trueOrFalse 추천상태 (best,worst,cancel)
     * @throws com.example.team_project.exception.BaseReviewNotFoundException baseReviewValidate 검증 실패시
     * @throws com.example.team_project.exception.BaseReviewAndUserNotFoundException reviewRecommendValidate 검증 실패시
     */
    void update(Long userId, Long baseReviewId, String trueOrFalse);
}
