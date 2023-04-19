package com.example.team_project.domain.domain.user.service.user;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UpdateUserInfoServiceImpl implements UpdateUserInfoService {
    private final UserRepository userRepository;

    UpdateUserInfoServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User updateUserInfo(String password, String newUserName, String newPhoneNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Optional<User> userOptional = userRepository.findByUserIdAndPassword(userId, password);
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("Invalid password"));

        return userRepository.save(new User(user.getId(), user.getUserId(), user.getPassword(), newUserName, newPhoneNumber));
    }
}
