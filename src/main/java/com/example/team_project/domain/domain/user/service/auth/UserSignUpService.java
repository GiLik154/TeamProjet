package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.user.service.auth.dto.UserSignUpDTO;

public interface UserSignUpService {
    void signUp(UserSignUpDTO userSignUpDTO);
}
