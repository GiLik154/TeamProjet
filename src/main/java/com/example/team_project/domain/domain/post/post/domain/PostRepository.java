package com.example.team_project.domain.domain.post.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);

    List<Post> findBySituation(String create);
}
