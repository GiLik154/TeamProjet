package com.example.team_project;

import com.example.team_project.domain.user.domain.User;
import com.example.team_project.domain.user.domain.UserRepository;
import com.example.team_project.domain.user.dto.PostDto;
import com.example.team_project.domain.user.service.JoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class JoinTest {

    @Autowired
    private JoinService joinService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void write() {
        //Given
        PostDto postDto = new PostDto("test123", "asd");

        //When
        joinService.write(postDto);

        //Then
        User saveUser = userRepository.findByName("test123");
        System.out.println("saveudsers:" + saveUser);

    }



}

