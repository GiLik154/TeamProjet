package com.example.team_project.domain.post.service.update;

public interface PostUpdateService {
    boolean update(Long postId, String content, String postCategory);
}
