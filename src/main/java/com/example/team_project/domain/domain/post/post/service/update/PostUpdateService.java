package com.example.team_project.domain.domain.post.post.service.update;

import com.example.team_project.domain.domain.post.post.service.PostDto;
import org.springframework.web.multipart.MultipartFile;

public interface PostUpdateService {
    boolean update(Long userId, Long postId, PostDto dto, MultipartFile file);
}
