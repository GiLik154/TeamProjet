package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.user.domain.User;

public interface UserSignUpService {
    void signUp(String userId, String password, String userName, String email, String phoneNumber);
}
