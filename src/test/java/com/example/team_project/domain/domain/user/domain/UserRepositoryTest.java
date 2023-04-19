package com.example.team_project.domain.domain.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertUser() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            User user = User.builder()
                    .userId("User" + i)
                    .password(passwordEncoder.encode("1111"))
                    .userName("userName" + i)
                    .email("email" + i + "@aaaa.bbbb")
                    .build();

            userRepository.save(user);
        });
    }

}