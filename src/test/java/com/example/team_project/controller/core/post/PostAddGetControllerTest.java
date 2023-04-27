package com.example.team_project.controller.core.post;

import com.example.team_project.controller.interceptor.UserLoginInterceptor;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostAddGetControllerTest {
    private final PostAddController postAddController;
    private final UserRepository userRepository;
    private final UserLoginInterceptor userLoginInterceptor;

    private MockMvc mockMvc;

    @Autowired
    PostAddGetControllerTest(PostAddController postAddController, UserRepository userRepository, UserLoginInterceptor userLoginInterceptor) {
        this.postAddController = postAddController;
        this.userRepository = userRepository;
        this.userLoginInterceptor = userLoginInterceptor;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(postAddController)
                .addInterceptors(userLoginInterceptor)
                .build();
    }
    @Test
    void Get_게시물_생성_페이지_정상작동() throws Exception {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/post/add")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/post/add"));
    }

    @Test
    void Get_게시물_생성_페이지_비로그인() throws Exception {

        MockHttpServletRequestBuilder builder = get("/post/add");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));
    }
}