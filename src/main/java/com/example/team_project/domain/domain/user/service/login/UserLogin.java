package com.example.team_project.domain.domain.user.service.login;


import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLogin {

    private final UserRepository userRepository;

    public void userLogin(Long userId){

        userRepository.findById(userId);

    }
}
