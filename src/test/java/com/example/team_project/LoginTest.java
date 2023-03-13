package com.example.team_project;

import com.example.team_project.domain.user.domain.UserRepository;
import com.example.team_project.domain.user.dto.PostDto;
import com.example.team_project.domain.user.service.JoinService;
import com.example.team_project.domain.user.service.LoginService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class LoginTest {

    private final JoinService joinService;
    private final UserRepository userRepository;
    private final LoginService loginService;
    @Autowired
    public LoginTest(JoinService joinService, UserRepository userRepository,LoginService loginService) {
        this.joinService = joinService;
        this.userRepository = userRepository;
        this.loginService = loginService;
    }


}
