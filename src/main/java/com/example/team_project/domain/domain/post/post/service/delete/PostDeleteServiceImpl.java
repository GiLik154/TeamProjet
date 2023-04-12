package com.example.team_project.domain.domain.post.post.service.delete;

import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
@RequiredArgsConstructor
public class PostDeleteServiceImpl implements PostDeleteService {
    private final PostRepository postRepository;

    /**
     * 유저 고유번호 와 게시물의 고유번호 ,유저의 비밀번호를 입력받음
     * 게시글의 고유번호로 게시글의 가져와서 delete() 함수를 실행
     */
    @Override
    public boolean delete(Long userId, Long postId) {
        AtomicBoolean result = new AtomicBoolean(false); // boolean 값을 저장할 AtomicBoolean 객체 생성

        postRepository.findById(postId).ifPresent(post -> {
            if (userId == post.getUser().getId()) {
                post.delete();
                result.set(true);
            }
        });
        return result.get();
    }
}
