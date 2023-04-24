package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.controller.core.user.dto.UserJoinDTO;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.dto.UserSignUpDto;
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

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByUserId(userSignUpDto.getUserId()).isPresent()) {
            throw new Exception("이미 존재하는 유저ID입니다.");
        }

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByPhoneNumber(userSignUpDto.getPhoneNumber()).isPresent()) {
            throw new Exception("이미 존재하는 전화번호입니다.");
        }

        User user = User.builder()
                .userId(userSignUpDto.getUserId())
                .password(userSignUpDto.getPassword())
                .userName(userSignUpDto.getUserName())
                .email(userSignUpDto.getEmail())
                .phoneNumber(userSignUpDto.getPhoneNumber())
                .role(Role.USER)
                .userGrade(UserGrade.SILVER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    /**
     * 아이디 중복 체크
     */
    public boolean checkUserIdDuplicate(String userChkId) {
        return userRepository.existsByUserId(userChkId);
    }

}
