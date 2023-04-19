package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.user.domain.User;

public interface UserLoginService {
    User loginUser(String userName, String password);
}
