package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.PasswordNotMatchedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @return null이면 로그인 실패
     */
    @Override
    public User login(String userId, String password) {

        return userRepository.findByUserId(userId)
                .filter(u -> passwordEncoder.matches(u.getPassword(),password))
                .orElse(null);
    }
}