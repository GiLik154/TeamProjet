package com.example.team_project.domain.domain.review.kinds;

import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;

public interface ReviewJoinKindsService {
    ReviewToKinds returnReviewToKindsEntity(Long KindsId);
}
