package com.example.team_project.domain.user.service.idcheckservice;

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


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IdCheckServiceTest {
    private final UserRepository userRepository;
    private final IdCheckService idCheckService;
    @Autowired
    IdCheckServiceTest(UserRepository userRepository, IdCheckService idCheckService) {
        this.userRepository = userRepository;
        this.idCheckService = idCheckService;
    }

    @Test
    void 아이디_확인_정상작동() {
        //given
        User user = new User("아이디","1234");
        User user2 = new User("아이디2","4321");
        userRepository.save(user);
        userRepository.save(user2);
        //when
        UserDto dto = new UserDto("아이디","2345");
        Exception e = assertThrows(BadCredentialsException.class, () ->
                idCheckService.idCheck(dto)
        );
        //then
        assertEquals("중복된 아이디",e.getMessage());
    }
}