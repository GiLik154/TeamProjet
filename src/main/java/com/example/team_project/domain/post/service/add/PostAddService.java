package com.example.team_project.domain.post.service.add;

public interface PostAddService {
    boolean add(Long userId, String content, String category);
}
