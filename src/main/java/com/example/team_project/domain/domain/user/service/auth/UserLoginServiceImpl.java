package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    private final UserRepository userRepository;

    public UserLoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> loginUser(String userId, String password) {
        Optional<User> userOptional = userRepository.findByUserIdAndPassword(userId, password);
        return userOptional;
    }
}
