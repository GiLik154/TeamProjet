package com.example.team_project.domain.domain.post.post.service.delete;

public interface PostDeleteService {
    /**
     * 유저 고유번호 와 게시물의 고유번호 ,유저의 비밀번호를 입력받음
     * 게시글의 고유번호로 게시글의 가져와서 delete() 함수를 실행
     * @param userId 유저의 고유번호
     * @param postId 게시글의 고유번호
     */
    boolean delete(Long userId, Long postId);

}
