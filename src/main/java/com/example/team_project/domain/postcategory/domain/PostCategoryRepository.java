package com.example.team_project.domain.postcategory.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCategoryRepository extends JpaRepository<PostCategory,Long> {
    Optional<PostCategory> findByName(String postCategory);
}
