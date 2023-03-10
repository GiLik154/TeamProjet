package com.example.team_project.domain.user.service.joinservice;

import com.example.team_project.controller.uesr.dto.UserDto;
import com.example.team_project.domain.user.domain.User;
import com.example.team_project.domain.user.domain.UserRepository;
import com.example.team_project.domain.user.service.loginservice.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JoinServiceImplTest {
    private final UserRepository userRepository;
    private final JoinService joinService;
    @Autowired
    JoinServiceImplTest(UserRepository userRepository, JoinService joinService) {
        this.userRepository = userRepository;
        this.joinService = joinService;
    }
    @Test
    void 회원가입_정상작동() {
        UserDto dto = new UserDto("아이디","1234");

        joinService.validateJoin(dto);

        assertTrue (userRepository.existsByNameAndPassword(dto.getName(),dto.getPass()));
    }
}