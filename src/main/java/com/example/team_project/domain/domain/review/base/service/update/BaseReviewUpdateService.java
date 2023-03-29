package com.example.team_project.domain.domain.review.base.service.update;

import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;

public interface BaseReviewUpdateService {
    void update(Long baseReviewId, Long userId, ReviewDto reviewDto);
}
