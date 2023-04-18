package com.example.team_project.domain.domain.post.post.service.update;

import com.example.team_project.domain.domain.post.post.service.PostDto;
import org.springframework.web.multipart.MultipartFile;

public interface PostUpdateService {
    /**
     * 유저의 고유번호,게시글의 고유번호,postDto,이미지 파일을 입력받음
     * 게시글의 고유번호로 게시글을 가져옴
     * update()함수를 실행하여 변경
     * @param userId 유저의 고유번호
     * @param postId 게시글의 고유번호
     * @param dto title,content,category 를 받음
     * @param file 이미지 파일
     * @throws IllegalArgumentException category 가 존재하지 않을때 exception 발생
     */
    boolean update(Long userId, Long postId, PostDto dto, MultipartFile file);
}
