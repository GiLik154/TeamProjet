package com.example.team_project.domain.domain.review.product.domain;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<BaseReview,Long> {
}
