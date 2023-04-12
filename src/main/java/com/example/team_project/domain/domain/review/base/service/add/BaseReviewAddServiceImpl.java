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
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class BaseReviewAddServiceImpl<T> implements BaseReviewAddService {
    private final BaseReviewRepository baseReviewRepository;
    private final UserRepository userRepository;
    private final Map<String, ReviewJoinKindsService> reviewJoinKindsServiceMap;
    private final ImageUploadService imageUploadService;

    /**
     * 유저의 고유번호,리뷰작성글,이미지 파일을 받아 실행
     * 유저를 검증함
     * 유저의 고유번호를 이용해 유저가있으면 객체를 가져옴
     * 반환된 ReviewToKinds 저장
     * 이미지 파일을 업로드
     */
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
