package com.example.team_project.domain.domain.review.base.service.add;

import com.example.team_project.domain.domain.review.base.service.dto.ReviewDto;
import org.springframework.web.multipart.MultipartFile;

public interface BaseReviewAddService {
    /**
     * 유저의 고유번호,리뷰작성글,이미지 파일을 받아 실행
     * 유저를 검증함
     * 유저의 고유번호를 이용해 유저가있으면 객체를 가져옴
     * 반환된 ReviewToKinds 저장
     * 이미지 파일을 업로드
     * 정보를 받아 종류와 고유번호를 받음
     * 종류가 게시글이면 게시글리뷰 상품이면 상품리뷰로 저장
     * 고유번호로 종류의 객체를 가져와 반환
     * @param userId 유저의 고유번호
     * @param reviewDto title(제목),content(내용),kinds(종류),kindsId(종류의 고유번호) 가 있음
     * @param file 이미지 파일
     */
    void add(Long userId, ReviewDto reviewDto, MultipartFile file);
}
