package com.example.team_project.domain.domain.product.countduplication.domain;

import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCountRepository extends JpaRepository<LikeCountCheck, Long> {

    Optional<LikeCountCheck> findByUserIdAndProductId(Optional<User> user, Optional<Product> product);

    Optional<LikeCountCheck> deleteByProductId(Optional<Product> product);
}
