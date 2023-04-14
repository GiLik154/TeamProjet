package com.example.team_project.domain.domain.review.base.service.update;

import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import org.springframework.web.multipart.MultipartFile;

public interface BaseReviewUpdateService {
    /**
     * 베이스 리뷰의 고유번호롤 베이스리뷰가 있으면 가져옴
     * 베이스리뷰의 유저와 받아온 유저의 고유번호가 같으면
     * 정보를 입력받아 업데이트
     * @param baseReviewId 리뷰의 고유번호
     * @param userId 유저의 고유번호
     * @param reviewDto title(제목),content(내용),kinds(종류),kindsId(종류의 고유번호) 가 있음
     * @param file 이미지 파일
     */
    void update(Long baseReviewId, Long userId, ReviewDto reviewDto, MultipartFile file);
}
