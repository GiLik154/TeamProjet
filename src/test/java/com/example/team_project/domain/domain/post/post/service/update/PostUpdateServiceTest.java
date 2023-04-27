package com.example.team_project.domain.domain.post.post.service.update;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.post.post.service.PostDto;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PostCategoryStatus;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostUpdateServiceTest {
    private final PostUpdateService postUpdateService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Autowired
    PostUpdateServiceTest(PostUpdateService postUpdateService, UserRepository userRepository, PostRepository postRepository, PostCategoryRepository postCategoryRepository) {
        this.postUpdateService = postUpdateService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postCategoryRepository = postCategoryRepository;
    }

    @Test
    void 포스트_업데이트_정상작동() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        PostCategory postCategory = new PostCategory(PostCategoryStatus.PRODUCT_INQUIRY);
        postCategoryRepository.save(postCategory);

        Post post = new Post("title", "content", "time", user, postCategory);
        postRepository.save(post);
        System.out.println("sdfsdf"+postRepository.findById(post.getId()).get().getContent());

        PostDto dto = new PostDto("title2", "content2", "SHIPPING_INQUIRY");

        boolean isTrue = postUpdateService.update(user.getId(), post.getId(), dto, file);

        Post testPost = postRepository.findById(post.getId()).get();

        assertTrue(isTrue);
        assertEquals("title2", testPost.getTitle());
        assertEquals("content2", testPost.getContent());
        assertEquals(PostCategoryStatus.SHIPPING_INQUIRY, testPost.getPostCategory().getName());
    }

    @Test
    void 포스트_업데이트_유저다름() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("testId1", "testPw1", "testNane", "testEmail1" ,"testNumber1");
        userRepository.save(user);
        User user2 = new User("testId2", "testPw2", "testNane", "testEmail2", "testNumber2");
        userRepository.save(user2);

        PostCategory postCategory = new PostCategory(PostCategoryStatus.PRODUCT_INQUIRY);

        Post post = new Post("title", "content", "time", user, postCategory);
        postRepository.save(post);

        PostDto dto = new PostDto("title2", "content2", "SHIPPING_INQUIRY");

        boolean isFalse = postUpdateService.update(user2.getId(), post.getId(), dto, file);

        Post testPost = postRepository.findById(post.getId()).get();

        assertFalse(isFalse);
        assertNotEquals("content2", testPost.getContent());
        assertNotEquals(PostCategoryStatus.SHIPPING_INQUIRY, testPost.getPostCategory().getName());
    }

}