package com.example.team_project.domain.domain.user.service.Login;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserLoginService {
    private final UserRepository userRepository;

    public UserLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String userId, String password) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Invalid ID or Password"));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid ID or Password");
        }
        return user;
    }
}
