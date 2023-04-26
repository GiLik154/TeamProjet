package com.example.team_project.domain.domain.user.service.user;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Transactional
@Service
@RequiredArgsConstructor
public class UpdateUserInfoServiceImpl implements UpdateUserInfoService {

    private final UserRepository userRepository;

    @Override

    public void updateUserInfo(Long userId, String password, String userName, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        User updatedUser = new User(password, userName, phoneNumber);
        user.modify(updatedUser);


        userRepository.save(updatedUser);
    }
}
