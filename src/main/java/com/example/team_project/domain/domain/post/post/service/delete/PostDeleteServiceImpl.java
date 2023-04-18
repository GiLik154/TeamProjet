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


    @Override
    public boolean delete(Long userId, Long postId) {
        AtomicBoolean result = new AtomicBoolean(false); // boolean 값을 저장할 AtomicBoolean 객체 생성
        
        postRepository.findById(postId).ifPresent(post -> {
            if (userId.equals(post.getUser().getId())) {
                System.out.println("일로 왜 안와??");
                post.delete();
                result.set(true);
            }
        });
        return result.get();
    }
}
