package com.example.team_project.domain.domain.post.post.service.add;

import com.example.team_project.domain.domain.post.post.service.PostDto;
import org.springframework.web.multipart.MultipartFile;

public interface PostAddService {
    /**
     * 컨트롤단에서 postDto 와 userId, 이미지 파일을 가지고 생성
     * 유저의 아이디 검증
     * 이미지파일 업로드
     * 저장후 결과값 true 로 보냄
     * @param userId 유저의 고유번호
     * @param dto  title, content, category 를 받음
     * @param file  이미지 파일
     */
    boolean add(Long userId, PostDto dto, MultipartFile file);
}
