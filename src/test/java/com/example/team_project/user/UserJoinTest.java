package com.example.team_project.user;

import com.example.team_project.domain.user.domain.User;
import com.example.team_project.domain.user.domain.UserRepository;
import com.example.team_project.domain.user.dto.UserDto;
import com.example.team_project.domain.user.service.JoinService;
import com.example.team_project.domain.user.service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserJoinTest {

    private final JoinService joinService;
    private final LoginService loginService;
    private final UserRepository userRepository;

    @Autowired
    public UserJoinTest(JoinService joinService, LoginService loginService, UserRepository userRepository) {
        this.joinService = joinService;
        this.loginService = loginService;
        this.userRepository = userRepository;
    }

    @Test
    void join_true(){
        //given
        UserDto userDto = new UserDto("testname", "testpw");

        //when
        User user = joinService.add(userDto);

        //then
        assertEquals("testname", user.getName());
        assertEquals("testpw", user.getPassword());

    }

    @Test
    void join_false(){
        //given
        User user = new User("name", "password");
        userRepository.save(user);
        UserDto userDto = new UserDto("name", "password");

        //when
        RuntimeException exception = assertThrows(RuntimeException.class, ()->
                joinService.add(userDto)
        );

        //then
        assertEquals("중복되는 아이디 입니다", exception.getMessage());

    }

}
