package com.example.team_project.domain.user.service.idcheckservice;

import com.example.team_project.controller.uesr.dto.UserDto;
import com.example.team_project.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class IdCheckServiceImpl implements IdCheckService {
    private final UserRepository userRepository;

    @Override
    public void idCheck(UserDto dto) {
        if (userRepository.existsByName(dto.getName())) {
            throw new BadCredentialsException("중복된 아이디");
        }
    }
}
