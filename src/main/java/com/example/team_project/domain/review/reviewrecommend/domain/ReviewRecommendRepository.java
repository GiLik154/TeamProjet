package com.example.team_project.domain.review.reviewrecommend.domain;

import com.example.team_project.domain.review.basereview.domain.BaseReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRecommendRepository extends JpaRepository<BaseReview,Long> {
}
