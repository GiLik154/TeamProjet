package com.example.team_project.domain.domain.user.service.user;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.service.user.UserService;
import com.example.team_project.exception.PasswordNotMatchedException;
import com.example.team_project.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입 메서드
    @Override
    public User createUser(String userId, String password, String userName, String phoneNumber) {
        User user = new User(userId, password, userName, phoneNumber);
        return userRepository.save(user);
    }

    //로그인 메서드
    @Override
    public User login(String userId, String password) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException());

        if (!user.getPassword().equals(password)) {
            throw new PasswordNotMatchedException();
        }

        return user;
    }

    // 사용자 정보 수정 메서드
    @Override
    public User updateUser(String password, String userName, String phoneNumber, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        User updatedUser = new User(
                user.getUserId(),
                password,
                userName,
                phoneNumber,
                user.getUserGrade(),
                passwordEncoder);

        return userRepository.save(updatedUser);
    }

    // 사용자 삭제 메서드
    @Override
    public void deleteUser(String userId, String password) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException());

        if (!user.getPassword().equals(password)) {
            throw new PasswordNotMatchedException();
        }

        userRepository.delete(user);
    }
}

