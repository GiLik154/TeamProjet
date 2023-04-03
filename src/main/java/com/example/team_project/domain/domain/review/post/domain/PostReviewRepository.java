package com.example.team_project.domain.domain.review.post.domain;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReviewRepository extends JpaRepository<BaseReview,Long> {
}
