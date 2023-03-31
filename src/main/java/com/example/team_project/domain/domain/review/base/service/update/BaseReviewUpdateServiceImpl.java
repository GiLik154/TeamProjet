package com.example.team_project.domain.domain.review.base.service.update;

import com.example.team_project.domain.domain.image.service.ImageUploadService;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import com.example.team_project.domain.domain.review.kinds.ReviewJoinKindsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class BaseReviewUpdateServiceImpl<T> implements BaseReviewUpdateService {
    private final BaseReviewRepository baseReviewRepository;
    private final Map<String, ReviewJoinKindsService> reviewJoinKindsServiceMap;
    private final ImageUploadService imageUploadService;

    @Override
    public void update(Long baseReviewId, Long userId, ReviewDto reviewDto, MultipartFile file) {

        baseReviewRepository.findById(baseReviewId).ifPresent(baseReview -> {
            if (baseReview.getUser().getId() == userId) {
                baseReview.update(
                        reviewDto.getTitle(),
                        reviewDto.getContent(),
                        getTime(),
                        getType(reviewDto));
                imageUploadService.upload(baseReview.getTitle(),file,baseReview);
            }
        });
    }

    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:ss"));
    }

    private ReviewToKinds getType(ReviewDto reviewDto) {
        ReviewJoinKindsService reviewJoinKindsService = reviewJoinKindsServiceMap.get(reviewDto.getKinds());
        return reviewJoinKindsService.returnReviewToKindsEntity(reviewDto.getKindsId());
    }
}
