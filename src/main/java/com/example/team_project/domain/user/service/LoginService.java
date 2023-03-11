package com.example.team_project.domain.user.service;

import com.example.team_project.domain.user.domain.User;
import com.example.team_project.domain.user.domain.UserRepository;
import com.example.team_project.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    /** 유저 로그인 **/
    public User enter(UserDto userDto){

        Optional<User> userOptional = userRepository.findByNameAndPassword(userDto.getName(), userDto.getPassword());

        if (userOptional.isPresent()){
            User user = userOptional.get();

            return user;
        }

        throw  new RuntimeException("잘못된 입력입니다");
    }
}
