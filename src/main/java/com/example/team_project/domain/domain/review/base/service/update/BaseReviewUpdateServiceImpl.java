package com.example.team_project.domain.domain.review.base.service.update;

import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import com.example.team_project.domain.domain.review.kinds.ReviewJoinKindsService;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class BaseReviewUpdateServiceImpl<T> implements BaseReviewUpdateService {
    private final BaseReviewRepository baseReviewRepository;
    private final UserRepository userRepository;
    private final Map<String, ReviewJoinKindsService> reviewJoinKindsServiceMap;

    @Override
    public void update(Long baseReviewId, Long userId, ReviewDto reviewDto) {

        //유저 검증 후

        baseReviewRepository.findById(baseReviewId).ifPresent(baseReview -> {

           baseReview.update(reviewDto.getContent(),
                    getTime(),
                    reviewDto.getImagePath(),
                    getType(reviewDto));
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
