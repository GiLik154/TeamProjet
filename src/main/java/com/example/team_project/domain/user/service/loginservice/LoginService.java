package com.example.team_project.domain.user.service.loginservice;

import com.example.team_project.controller.uesr.dto.UserDto;

public interface LoginService {

    void validateLogin(UserDto dto);
}
