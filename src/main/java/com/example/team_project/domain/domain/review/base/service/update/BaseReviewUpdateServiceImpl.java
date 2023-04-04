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

    /**
     * 베이스 리뷰의 고유번호롤 베이스리뷰가 있으면 가져옴
     * 베이스리뷰의 유저와 받아온 유저의 고유번호가 같으면
     * 정보를 입력받아 업데이트
     */
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

    /**
     * 서버의 시간을 저장
     */
    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:ss"));
    }

    /**
     * 정보를 받아 종류와 고유번호를 받음
     * 종류가 게시글이면 게시글리뷰 상품이면 상품리뷰로 저장
     * 고유번호로 종류의 객체를 가져와 반환
     */
    private ReviewToKinds getType(ReviewDto reviewDto) {
        ReviewJoinKindsService reviewJoinKindsService = reviewJoinKindsServiceMap.get(reviewDto.getKinds());
        return reviewJoinKindsService.returnReviewToKindsEntity(reviewDto.getKindsId());
    }
}
