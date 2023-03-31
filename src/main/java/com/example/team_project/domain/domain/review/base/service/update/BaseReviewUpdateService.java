package com.example.team_project.domain.domain.review.base.service.update;

import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import org.springframework.web.multipart.MultipartFile;

public interface BaseReviewUpdateService {
    void update(Long baseReviewId, Long userId, ReviewDto reviewDto, MultipartFile file);
}
