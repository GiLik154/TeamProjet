package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.controller.core.user.dto.UserJoinDTO;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.Role;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.UserInfoAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserSignUpServiceImpl implements UserSignUpService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public String join(UserJoinDTO user)  {

        User userEntity = new User(user.getUserId(), passwordEncoder.encode(user.getPassword()), user.getUserName(), user.getEmail(), user.getPhoneNumber());

        userRepository.save(userEntity);

        return userEntity.getUserId();

    }

    /**
     * 아이디 중복 체크
     */
    public boolean checkUserIdDuplicate(String userChkId) {
        return userRepository.existsByUserId(userChkId);
    }

}
