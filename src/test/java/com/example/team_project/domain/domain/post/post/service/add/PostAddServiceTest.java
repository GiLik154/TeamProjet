package com.example.team_project.domain.domain.post.post.service.add;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.post.post.service.PostDto;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostAddServiceTest {
    private final PostAddService postAddService;
    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PostRepository postRepository;

    @Autowired
    PostAddServiceTest(PostAddService postAddService, UserRepository userRepository, PostCategoryRepository postCategoryRepository, PostRepository postRepository) {
        this.postAddService = postAddService;
        this.userRepository = userRepository;
        this.postCategoryRepository = postCategoryRepository;
        this.postRepository = postRepository;
    }

    @Test
    void 게시글_추가_정상작동() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("testName", "testPassword");
        userRepository.save(user);

        PostCategory postCategory = new PostCategory("testCategory");
        postCategoryRepository.save(postCategory);

        PostDto dto = new PostDto("title", "content", "testCategory");

        boolean isAdd = postAddService.add(user.getId(), dto, file);

        Optional<Post> post = postRepository.findByUserId(user.getId());
        assertTrue(isAdd);
        assertEquals("testCategory", post.get().getPostCategory().getName());
        assertEquals("content", post.get().getContent());
        assertEquals(user.getId(), post.get().getUser().getId());
        assertEquals("create", post.get().getSituation());
        assertNotNull(post.get().getImagePath());
    }

    @Test
    void 게시글_추가_카테고리_오류() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("testName", "testPassword");
        userRepository.save(user);

        PostCategory postCategory = new PostCategory("testCategory");
        postCategoryRepository.save(postCategory);

        PostDto dto = new PostDto("title", "content", "fail");

        boolean isAdd = postAddService.add(user.getId(), dto, file);

        assertFalse(isAdd);
    }

    @Test
    void 게시글_추가_유저_없을때_오류() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        PostCategory postCategory = new PostCategory("testCategory");
        postCategoryRepository.save(postCategory);

        PostDto dto = new PostDto("title", "content", "testCategory");

        Exception e = assertThrows(UserNotFoundException.class, () ->
                postAddService.add(0L, dto, file)
        );

        assertEquals("This user could not be found", e.getMessage());
    }
}