package com.example.team_project.domain.domain.review.base.service.add;

import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import org.springframework.web.multipart.MultipartFile;

public interface BaseReviewAddService {
    void add(Long userId, ReviewDto reviewDto, MultipartFile file);
}
