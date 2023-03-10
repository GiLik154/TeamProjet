package com.example.team_project.domain.user.service.joinservice;

import com.example.team_project.controller.uesr.dto.UserDto;
import com.example.team_project.domain.user.domain.User;
import com.example.team_project.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService{
    private final UserRepository userRepository;
    @Override
    public  void validateJoin(UserDto dto){
        User user = new User(dto.getName(), dto.getPass());
        userRepository.save(user);
    }
}
