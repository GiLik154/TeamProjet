package com.example.team_project.domain.user.service;


import com.example.team_project.domain.user.domain.UserRepository;
import com.example.team_project.domain.user.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final UserRepository userRepository;

    public boolean login(PostDto postDto){
        long count = userRepository.countByNameAndPassword(postDto.getName(),postDto.getPassword());

        if(count==1){
            System.out.println("성공");
            return true;
        }else{
            System.out.println("실패");
            return false;
        }
    }


}
