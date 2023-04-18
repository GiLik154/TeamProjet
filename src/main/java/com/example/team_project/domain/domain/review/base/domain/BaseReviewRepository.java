package com.example.team_project.domain.domain.review.base.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BaseReviewRepository extends JpaRepository<BaseReview,Long> {
    Optional<BaseReview> findByUserId(Long id);

    List<BaseReview> findByReviewToKinds_PostReview_PostIdAndSituation(Long postId, String situation);
    Page<BaseReview> findByReviewToKinds_ProductReview_ProductIdAndSituation(Long productId, String situation, Pageable pageable);
}
