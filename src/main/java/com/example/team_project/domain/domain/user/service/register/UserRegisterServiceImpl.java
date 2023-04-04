package com.example.team_project.domain.domain.user.service.register;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;

import javax.persistence.EntityExistsException;

public class UserRegisterServiceImpl implements UserRegisterService {

    private final UserRepository userRepository;

    public UserRegisterServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(String userId, String password, String userName, String phoneNumber) {

        User user = new User(userId, password, userName, phoneNumber);
        try {
            userRepository.save(user);
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("이미 존재하는 ID입니다.", e);
        } catch (Exception e) {
            throw new RuntimeException("회원등록에 실패했습니다.", e);
        }

    }
}
