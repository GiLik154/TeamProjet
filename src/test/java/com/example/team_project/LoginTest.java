package com.example.team_project;

import com.example.team_project.domain.user.domain.UserRepository;
import com.example.team_project.domain.user.dto.PostDto;
import com.example.team_project.domain.user.service.JoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class LoginTest {

    private final JoinService joinService;
    private final UserRepository userRepository;
    @Autowired
    public LoginTest(JoinService joinService, UserRepository userRepository) {
        this.joinService = joinService;
        this.userRepository = userRepository;
    }
    
    @Test
    public void 로그인테스트_성공(){
        PostDto postDto = new PostDto("test123", "asd");
        joinService.write(postDto);
        
    }
    
}
