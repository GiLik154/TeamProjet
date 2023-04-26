package com.example.team_project.domain.domain.user.service.user;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class UpdateUserInfoServiceImpl implements UpdateUserInfoService {
    private final UserRepository userRepository;

    UpdateUserInfoServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updateUserInfo(Long userId, String password, String userName, String email, String phoneNumber) {
        User user = userRepository.findByIdAndPassword(userId, password)
                .orElseThrow(() -> new UserNotFoundException());

        user.modify(password, userName, email, phoneNumber);

    }
}
