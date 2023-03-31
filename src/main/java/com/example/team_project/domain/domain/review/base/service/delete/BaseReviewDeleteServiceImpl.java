package com.example.team_project.domain.domain.review.base.service.delete;

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

@Service
@Transactional
@RequiredArgsConstructor
public class BaseReviewDeleteServiceImpl<T> implements BaseReviewDeleteService {
    private final BaseReviewRepository baseReviewRepository;

    @Override
    public void delete(Long baseReviewId, Long userId, String password) {

       baseReviewRepository.findById(baseReviewId).ifPresent(baseReview -> {
           //사용자 검증 후

            baseReview.delete();
        });
    }
}
