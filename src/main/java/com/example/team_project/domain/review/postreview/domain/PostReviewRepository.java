package com.example.team_project.domain.review.postreview.domain;

import com.example.team_project.domain.review.basereview.domain.BaseReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReviewRepository extends JpaRepository<BaseReview,Long> {
}
