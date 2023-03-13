package com.example.team_project.domain.user.service;


import com.example.team_project.domain.user.domain.UserRepository;
import com.example.team_project.domain.user.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final UserRepository userRepository;

    public boolean login(PostDto postDto){
       // long count = userRepository.countByNameAndPassword(postDto.getName(),postDto.getPassword());

        if(userRepository.countByNameAndPassword(postDto.getName(),postDto.getPassword())==1){
            System.out.println("로그인성공");
            return true;
        }else{
            return false;
        }
    }





}
