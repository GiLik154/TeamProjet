package com.example.team_project.domain.user.service;

import com.example.team_project.domain.user.domain.User;
import com.example.team_project.domain.user.dto.PostDto;
import com.example.team_project.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean write(PostDto postDto){
        long count = userRepository.countByName(postDto.getName());

        if(count==1){
            System.out.println("중복됨");
            return false;
        }else{
            String encodePassword = passwordEncoder.encode(postDto.getPassword());
            User user = new User(postDto.getName(), postDto.getPassword());
            userRepository.save(user);
            return true;
        }
    }
}
