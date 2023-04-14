package com.example.team_project.domain.domain.review.base.service.delete;

import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BaseReviewDeleteServiceImpl implements BaseReviewDeleteService {
    private final BaseReviewRepository baseReviewRepository;

    @Override
    public void delete(Long baseReviewId, Long userId) {

       baseReviewRepository.findById(baseReviewId).ifPresent(baseReview -> {
            if(baseReview.getUser().getId().equals(userId)) {
                baseReview.delete();
            }
        });
    }
}
