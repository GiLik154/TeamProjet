package com.example.team_project.domain.domain.review.recommend.service.add;

public interface ReviewRecommendAddService {
    /**
     * 유저의 고유번호로 유저가 있으면 객체를 가져옴
     * baseReviewValidate 로 리뷰 검증
     * duplication 이 ture 가 아니면 객체 생성
     * ture 면 객체 생성 하지않고 false 반환
     * @param userId 유저의 고유번호
     * @param baseReviewId 리뷰의 고유번호
     * @param trueOrFalse 추천상태 (best,worst,cancel)
     * @throws com.example.team_project.exception.BaseReviewNotFoundException  baseReviewValidate 검증 실패시
     */
    boolean add(Long userId, Long baseReviewId, String trueOrFalse);
}
