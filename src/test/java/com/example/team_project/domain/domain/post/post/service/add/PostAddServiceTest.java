package com.example.team_project.domain.domain.post.post.service.add;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.post.post.service.PostDto;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PostCategoryStatus;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        PostDto dto = new PostDto("title", "content", "PRODUCT_INQUIRY");

        boolean isAdd = postAddService.add(user.getId(), dto, file);

        List<Post> post = postRepository.findByUserId(user.getId());
        assertTrue(isAdd);
        assertEquals(PostCategoryStatus.PRODUCT_INQUIRY, post.get(0).getPostCategory().getName());
        assertEquals("content", post.get(0).getContent());
        assertEquals(user.getId(), post.get(0).getUser().getId());
        assertEquals("create", post.get(0).getSituation());
        assertNotNull(post.get(0).getImagePath());
    }

    @Test
    void 게시글_추가_카테고리_오류() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        PostDto dto = new PostDto("title", "content", "fail");

        boolean isAdd = postAddService.add(user.getId(), dto, file);

        assertFalse(isAdd);
    }

    @Test
    void 게시글_추가_유저_없을때_오류() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        PostDto dto = new PostDto("title", "content", "PRODUCT_INQUIRY");

        Exception e = assertThrows(UserNotFoundException.class, () ->
                postAddService.add(0L, dto, file)
        );

        assertEquals("This user could not be found", e.getMessage());
    }
}