package com.example.team_project.domain.domain.review.rcommend.domain;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRecommendRepository extends JpaRepository<BaseReview,Long> {
}
