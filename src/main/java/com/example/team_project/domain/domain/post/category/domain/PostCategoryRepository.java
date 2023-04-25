package com.example.team_project.domain.domain.post.category.domain;

import com.example.team_project.enums.PostCategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCategoryRepository extends JpaRepository<PostCategory,PostCategoryStatus> {
    Optional<PostCategory> findByName(PostCategoryStatus postCategory);
}
