package com.example.team_project.domain.domain.post.post.service.update;

import com.example.team_project.domain.domain.image.service.ImageUploadService;
import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.post.post.service.PostDto;
import com.example.team_project.enums.PostCategoryStatus;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
@RequiredArgsConstructor
public class PostUpdateServiceImpl implements PostUpdateService {
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final ImageUploadService imageUploadService;

    @Override
    public boolean update(Long userId, Long postId, PostDto dto, MultipartFile file) {

        PostCategoryStatus getStatus;
        try {
            getStatus = PostCategoryStatus.valueOf(dto.getCategory());
        } catch (IllegalArgumentException e) {
            return false;
        }

        AtomicBoolean result = new AtomicBoolean(false); // boolean 값을 저장할 AtomicBoolean 객체 생성

        postRepository.findById(postId).ifPresent(post -> {
            if (post.getUser().getId().equals(userId)) {
                post.update(dto.getTitle(), dto.getContent(), getTime(), getCategory(getStatus));
                imageUploadService.upload(post.getTitle(), file, post);
                result.set(true);
            }
        });
        return result.get();
    }

    /**
     * 서버의 시간을 데이터베이스에 저장
     */
    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:ss"));
    }

    /**
     * 게시글의 종류를 스트링으로 받음
     * postCategoryRepository 에서 게시글을 찾음
     * 존재하지 않으면 IllegalArgumentException 이 발생
     */
    private PostCategory getCategory(PostCategoryStatus status) {
        return postCategoryRepository.findByName(status)
                .orElseGet(() -> {
                    PostCategory postCategory = new PostCategory(status);
                    return postCategoryRepository.save(postCategory);
                });
    }
}
