package com.example.team_project.domain.user.service.loginservice;

import com.example.team_project.controller.uesr.dto.UserDto;
import com.example.team_project.domain.user.domain.User;
import com.example.team_project.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoginServiceTest {
    private UserRepository userRepository;
    private LoginService loginService;
    @Autowired
    LoginServiceTest(UserRepository userRepository, LoginService loginService) {
        this.userRepository = userRepository;
        this.loginService = loginService;
    }
    @Test
    void 로그인_정상작동(){
        User user = new User("아이디","1234");
        userRepository.save(user);

        UserDto dto = new UserDto("아이디","1234");
        //then
        assertDoesNotThrow(() -> loginService.validateLogin(dto));
    }
    @Test
    void 로그인_아이디_다름(){
        User user = new User("아이디","1234");
        userRepository.save(user);

        UserDto dto = new UserDto("아이디2","1234");
        Exception e = assertThrows(BadCredentialsException.class, () ->
                loginService.validateLogin(dto)
        );

        assertEquals("아이디 확인",e.getMessage());
    }
    @Test
    void 로그인_비밀번호_다름(){
        User user = new User("아이디","1234");
        userRepository.save(user);

        UserDto dto = new UserDto("아이디","2345");
        Exception e = assertThrows(BadCredentialsException.class, () ->
                loginService.validateLogin(dto)
        );

        assertEquals("비밀번호 확인",e.getMessage());
    }

}