package com.example.team_project.domain.domain.review.kinds;

import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;

public interface ReviewJoinKindsService {
    /**
     * 정보를 받아 종류와 고유번호를 받음
     * 종류가 게시글이면 게시글리뷰 상품이면 상품리뷰로 저장
     * 고유번호로 종류의 객체를 가져와 반환
     * @param KindsId 종류의 고유번호
     */
    ReviewToKinds returnReviewToKindsEntity(Long KindsId);
}
