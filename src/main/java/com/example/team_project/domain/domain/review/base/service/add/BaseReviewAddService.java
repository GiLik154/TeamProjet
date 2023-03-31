package com.example.team_project.domain.domain.review.base.service.add;

import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;

public interface BaseReviewAddService {
    void add(Long userId, ReviewDto reviewDto);
}
