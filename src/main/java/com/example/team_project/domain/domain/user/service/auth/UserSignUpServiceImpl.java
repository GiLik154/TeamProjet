package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.service.auth.dto.UserSignUpDTO;
import com.example.team_project.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserSignUpServiceImpl implements UserSignUpService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserSignUpServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(UserSignUpDTO userSignUpDTO) {

        userRepository.duplication(userSignUpDTO);

        User user = new User(userSignUpDTO.getUserId(), userSignUpDTO.getPassword(),
                userSignUpDTO.getUserName(), userSignUpDTO.getEmail(), userSignUpDTO.getPhoneNumber());
        user.changePassword(passwordEncoder.encode(userSignUpDTO.getPassword()));
        user.addRole(Role.USER);
        userRepository.save(user);
    }
}
