package com.example.team_project.domain.domain.post.post.service.add;

import com.example.team_project.domain.domain.image.service.ImageUploadService;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.post.post.service.PostDto;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
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
        AtomicBoolean result = new AtomicBoolean(false); // boolean 값을 저장할 AtomicBoolean 객체 생성
        postCategoryRepository.findById(dto.getCategory()).ifPresent(category -> {
            User user = userRepository.validateUserId(userId);

            Post post = new Post(dto.getTitle(), dto.getContent(), getTime(), user, category); //점검
            imageUploadService.upload(post.getTitle(), file, post);
            postRepository.save(post);
            result.set(true);
        });
        return result.get();
    }

    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:ss"));
    }
}
