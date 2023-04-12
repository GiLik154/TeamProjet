package com.example.team_project.domain.domain.post.post.service.update;

import com.example.team_project.domain.domain.image.service.ImageUploadService;
import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.post.post.service.PostDto;
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

    /**
     * 유저의 고유번호,게시글의 고유번호,postDto,이미지 파일을 입력받음
     * 게시글의 고유번호로 게시글을 가져옴
     * update()함수를 실행하여 변경
     */
    @Override
    public boolean update(Long userId, Long postId, PostDto dto, MultipartFile file) {
        AtomicBoolean result = new AtomicBoolean(false); // boolean 값을 저장할 AtomicBoolean 객체 생성

        postRepository.findById(postId).ifPresent(post -> {
            if (post.getUser().getId().equals(userId)) {
                PostCategory getP_C = getPostCategory(dto.getCategory());
                post.update(dto.getTitle(), dto.getContent(), getTime(), getP_C);
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
    private PostCategory getPostCategory(String postCategory) {
        return postCategoryRepository.findByName(postCategory)
                .orElseThrow(() -> new IllegalArgumentException("Invalid PostCategory: " + postCategory));
    }
}
