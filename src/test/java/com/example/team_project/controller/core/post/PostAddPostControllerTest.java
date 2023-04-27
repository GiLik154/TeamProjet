package com.example.team_project.controller.core.post;

import com.example.team_project.controller.advice.UserExceptionAdvice;
import com.example.team_project.controller.interceptor.UserLoginInterceptor;
import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PostCategoryStatus;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostAddPostControllerTest {
    private final PostAddController postAddController;
    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PostRepository postRepository;
    private final UserExceptionAdvice userExceptionAdvice;
    private final UserLoginInterceptor userLoginInterceptor;

    private MockMvc mockMvc;

    @Autowired
    PostAddPostControllerTest(PostAddController postAddController,
                              UserRepository userRepository,
                              PostCategoryRepository postCategoryRepository,
                              PostRepository postRepository, UserExceptionAdvice userExceptionAdvice, UserLoginInterceptor userLoginInterceptor) {
        this.postAddController = postAddController;
        this.userRepository = userRepository;
        this.postCategoryRepository = postCategoryRepository;
        this.postRepository = postRepository;
        this.userExceptionAdvice = userExceptionAdvice;
        this.userLoginInterceptor = userLoginInterceptor;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(postAddController)
                .setControllerAdvice(userExceptionAdvice)
                .build();
    }

    @Test
    void Post_게시물_생성_정상작동() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = multipart("/post/add")
                .file(file)
                .param("title", "testTitle")
                .param("content", "testContent")
                .param("category", "PRODUCT_INQUIRY")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post/list"));

        Post post = postRepository.findByUserId(user.getId()).get(0);

        assertEquals("testTitle", post.getTitle());
        assertEquals("testContent", post.getContent());
        assertEquals(PostCategoryStatus.PRODUCT_INQUIRY, post.getPostCategory().getName());
        assertEquals(user.getId(), post.getUser().getId());
        assertNotNull(post.getImagePath());
    }
    @Test
    void Post_게시물_생성_유저_고유번호_다름() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        Long userId = 100L;

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        MockHttpServletRequestBuilder builder = multipart("/post/add")
                .file(file)
                .param("title", "testTitle")
                .param("content", "testContent")
                .param("category", "PRODUCT_INQUIRY")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMessage", "This user could not be found"));
    }
}