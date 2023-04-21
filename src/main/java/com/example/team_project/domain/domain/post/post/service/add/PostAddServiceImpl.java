package com.example.team_project.domain.domain.post.post.service.add;

import com.example.team_project.domain.domain.image.service.ImageUploadService;
import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.post.post.service.PostDto;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PostCategoryStatus;
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
public class PostAddServiceImpl implements PostAddService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final ImageUploadService imageUploadService;

    @Override
    public boolean add(Long userId, PostDto dto, MultipartFile file) {
        PostCategoryStatus getStatus;
        try {
            getStatus = PostCategoryStatus.valueOf(dto.getCategory());
        } catch (IllegalArgumentException e) {
            return false;
        }
            User user = userRepository.validateUserId(userId);

            Post post = new Post(dto.getTitle(), dto.getContent(), getTime(), user, getCategory(getStatus)); //점검

            imageUploadService.upload(post.getTitle(), file, post);
            postRepository.save(post);

        return true;
    }

    /**
     * 서버의 시간을 데이터베이스에 저장
     */
    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:ss"));
    }

    private PostCategory getCategory(PostCategoryStatus status){
        return postCategoryRepository.findByName(status)
                .orElseGet(() -> {
                    PostCategory postCategory = new PostCategory(status);
                    postCategoryRepository.save(postCategory);
                    return postCategory;
                });
    }
}
