package com.example.team_project.domain.domain.review.base.domain;

import com.example.team_project.domain.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface BaseReviewRepository extends JpaRepository<BaseReview,Long> {
    Optional<BaseReview> findByUser(User user);

}
