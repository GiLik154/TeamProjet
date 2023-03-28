package com.example.team_project.domain.review.productreview.domain;

import com.example.team_project.domain.review.basereview.domain.BaseReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<BaseReview,Long> {
}
