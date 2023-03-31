package com.example.team_project.domain.domain.review.base.service.delete;

import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;

public interface BaseReviewDeleteService {
    void delete(Long baseReviewId, Long userId, String password);
}
