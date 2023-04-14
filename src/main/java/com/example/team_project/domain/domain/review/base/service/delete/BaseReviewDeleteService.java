package com.example.team_project.domain.domain.review.base.service.delete;

public interface BaseReviewDeleteService {
    /**
     *  베이스 리뷰의 고유번호,유저의 고유번호,비밀번호를 받아옴
     *  베이스리뷰의 고유번호로 베이스리뷰를 가져옴
     *  베이스리뷰의 저장된 유저와 ,가져온 유저의 고유번호가 같을때
     *  delete()함수를 실행해 존재여부를 delete 로 변경
     * @param baseReviewId 리뷰의 고유번호
     * @param userId 유저의 고유번호
     */
    void delete(Long baseReviewId, Long userId);
}
