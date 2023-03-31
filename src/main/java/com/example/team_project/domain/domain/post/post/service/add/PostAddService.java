package com.example.team_project.domain.domain.post.post.service.add;

import com.example.team_project.domain.domain.post.post.service.PostDto;
import org.springframework.web.multipart.MultipartFile;

public interface PostAddService {
    boolean add(Long userId, PostDto dto, MultipartFile file);
}
