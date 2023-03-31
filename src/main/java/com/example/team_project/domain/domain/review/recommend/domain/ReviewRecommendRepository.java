package com.example.team_project.domain.domain.review.recommend.domain;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRecommendRepository extends JpaRepository<ReviewRecommend,Long> {
    Optional<ReviewRecommend> findByUserAndBaseReview(User user, BaseReview baseReview);
}