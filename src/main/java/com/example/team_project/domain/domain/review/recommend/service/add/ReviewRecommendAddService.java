package com.example.team_project.domain.domain.review.recommend.service.add;

public interface ReviewRecommendAddService {
    void add(Long userId, Long baseReviewId, String trueOrFalse);
}
