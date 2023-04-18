package com.example.team_project.domain.domain.user.service.user;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class DeleteUserServiceImpl implements DeleteUserService {
    private final UserRepository userRepository;

    DeleteUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void deleteUser(String userId, String password) {
        Optional<User> userOptional = userRepository.findByUserIdAndPassword(userId, password);
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("Invalid user information"));
        userRepository.delete(user);
    }
}
