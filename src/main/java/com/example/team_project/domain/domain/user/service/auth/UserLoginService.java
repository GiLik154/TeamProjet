package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.PasswordNotMatchedException;
import com.example.team_project.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long loadUserByUsername(String userId, String password) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);

        if (!user.isValidPassword(password, passwordEncoder)) {
            throw new PasswordNotMatchedException();
        }
        return user.getId();

    }
}
