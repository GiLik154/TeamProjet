package com.example.team_project.domain.domain.user.domain;

import com.example.team_project.enums.Role;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    UserRepositoryTest(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    public void 회원_추가_테스트() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            String userId = "testId"+i;
            String password = passwordEncoder.encode("12345678");
            String userName = "testName";
            String email = "testEmail"+i+"@test.com";
            String phoneNumber = "01012345678";

            User user = new User(userId, password, userName, email, phoneNumber);
            user.addRole(Role.USER);

            if (i >= 90) {
                user.addRole(Role.ADMIN);
            }

            userRepository.save(user);
        });
    }

}