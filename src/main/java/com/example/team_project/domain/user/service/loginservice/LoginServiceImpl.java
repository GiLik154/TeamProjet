package com.example.team_project.domain.user.service.loginservice;

import com.example.team_project.controller.uesr.dto.UserDto;
import com.example.team_project.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    @Override
    public void validateLogin(UserDto dto) {
        if (!userRepository.existsByName(dto.getName())) {
            throw new BadCredentialsException("아이디 확인");
        }else if(!userRepository.existsByPassword(dto.getPass())){
            throw new BadCredentialsException("비밀번호 확인");
        }
    }
}
