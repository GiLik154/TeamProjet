package com.example.team_project.domain.domain.review.base.service.add;

import com.example.team_project.domain.domain.image.service.ImageUploadService;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import com.example.team_project.domain.domain.review.kinds.ReviewJoinKindsService;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.UserNotFoundException;
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
public class BaseReviewAddServiceImpl<T> implements BaseReviewAddService {
    private final BaseReviewRepository baseReviewRepository;
    private final UserRepository userRepository;
    private final Map<String, ReviewJoinKindsService> reviewJoinKindsServiceMap;
    private final ImageUploadService imageUploadService;

    @Override
    public void add(Long userId, ReviewDto reviewDto, MultipartFile file) {
        userRepository.validateUserId(userId);
        userRepository.findById(userId).ifPresent(user -> {
            BaseReview baseReview = new BaseReview(user,
                    reviewDto.getTitle(),
                    reviewDto.getContent(),
                    getTime(),
                    getType(reviewDto));
            imageUploadService.upload(baseReview.getTitle(),file,baseReview);
            baseReviewRepository.save(baseReview);
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
