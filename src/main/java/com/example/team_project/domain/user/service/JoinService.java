package com.example.team_project.domain.user.service;

import com.example.team_project.domain.user.domain.User;
import com.example.team_project.domain.user.domain.UserRepository;
import com.example.team_project.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;

    /**
     * 회원 가입
     **/
    public User add(UserDto userDto) {

        validateName(userDto.getName());

        User user = new User(userDto.getName(), userDto.getPassword());
        userRepository.save(user);

        return user;

    }

    /**
     * 회원 가입 중복 확인
     **/
    public void validateName(String name) {

        if (userRepository.findByName(name).isPresent()) {

            throw new RuntimeException("중복되는 아이디 입니다");
        }
    }
}
