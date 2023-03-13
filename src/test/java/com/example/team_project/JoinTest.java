package com.example.team_project;

import com.example.team_project.domain.user.domain.UserRepository;
import com.example.team_project.domain.user.dto.PostDto;
import com.example.team_project.domain.user.service.JoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertFalse;

@SpringBootTest
@Transactional
public class JoinTest {

    private final JoinService joinService;
    private final UserRepository userRepository;

    @Autowired
    public JoinTest(JoinService joinService, UserRepository userRepository) {
        this.joinService = joinService;
        this.userRepository = userRepository;
    }

    @Test
    public void suc() {
        //Given
        PostDto postDto = new PostDto("test123", "asd");

        //When
        joinService.write(postDto);

        //Then
        assertTrue(userRepository.existsByNameAndPassword(postDto.getName(), postDto.getPassword()));
    }

}

