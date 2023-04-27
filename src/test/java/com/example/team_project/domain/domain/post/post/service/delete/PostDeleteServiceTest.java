package com.example.team_project.domain.domain.post.post.service.delete;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PostCategoryStatus;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostDeleteServiceTest {
    private final PostDeleteService postDeleteService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Autowired
    PostDeleteServiceTest(PostDeleteService postDeleteService, PostRepository postRepository, UserRepository userRepository, PostCategoryRepository postCategoryRepository) {
        this.postDeleteService = postDeleteService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postCategoryRepository = postCategoryRepository;
    }

    @Test
    void 게시물_삭제_정상작동() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        PostCategory postCategory = new PostCategory(PostCategoryStatus.PRODUCT_INQUIRY);
        postCategoryRepository.save(postCategory);

        Post post = new Post("testTitle", "testContent", "testTime", user, postCategory);
        postRepository.save(post);

        boolean isDel = postDeleteService.delete(user.getId(), post.getId());

        assertTrue(isDel);
        assertEquals("delete", post.getSituation());
    }

    @Test
    void 게시물_삭제_유저다름() {
        User user = new User("testId1", "testPw1", "testNane", "testEmail" ,"testNumber");
        User user2 = new User("testId2", "testPw2", "testNane", "testEmail", "testNumber");
        userRepository.save(user2);

        PostCategory postCategory = new PostCategory(PostCategoryStatus.PRODUCT_INQUIRY);
        postCategoryRepository.save(postCategory);

        Post post = new Post("testTitle", "testContent", "testTime", user, postCategory);
        postRepository.save(post);

        boolean isDel = postDeleteService.delete(user2.getId(), post.getId());

        assertFalse(isDel);
        assertNotEquals("delete", post.getSituation());
    }
}