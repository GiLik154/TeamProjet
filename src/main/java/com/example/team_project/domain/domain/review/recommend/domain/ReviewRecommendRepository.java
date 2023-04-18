package com.example.team_project.domain.domain.review.recommend.domain;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRecommendRepository extends JpaRepository<ReviewRecommend,Long> {
    Optional<ReviewRecommend> findByUserAndBaseReview(User user, BaseReview baseReview);

    boolean existsByUserAndBaseReview(User user, BaseReview baseReview);

    List<ReviewRecommend> findByUser(User user);

    Optional<ReviewRecommend> findByBaseReview_IdAndUser_Id(Long baseReviewId, Long userId);

    List<ReviewRecommend> findByBaseReview_ReviewToKinds_PostReview_PostIdAndUser_Id(Long postId, Long userId);

    List<ReviewRecommend> findByBaseReview_ReviewToKinds_ProductReview_ProductIdAndUser_Id(Long productId, Long userId);

    Integer countByBaseReview_IdAndRecommend(Long baseReviewId,String recommend);
}
