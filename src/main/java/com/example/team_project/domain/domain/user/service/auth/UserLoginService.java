package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.user.domain.User;

import java.util.Optional;

public interface UserLoginService {
    Long login(String userName, String password);
}
