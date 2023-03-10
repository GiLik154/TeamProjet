package com.example.team_project.domain.user.service;

import com.example.team_project.domain.user.domain.User;
import com.example.team_project.domain.user.dto.PostDto;
import com.example.team_project.domain.user.domain.JoinRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

    private final JoinRepositroy joinRepositroy;
    public void write(PostDto postDto){
        User user = new User(postDto.getName(), postDto.getPassword());
        joinRepositroy.save(user);
    }
}
