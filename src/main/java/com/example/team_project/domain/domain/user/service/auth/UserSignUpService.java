package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.user.service.auth.dto.UserSignUpDTO;
import com.example.team_project.exception.EmailAlreadyExistsException;
import com.example.team_project.exception.UserIdAlreadyExistsException;

public interface UserSignUpService {
    void signUp(UserSignUpDTO userSignUpDTO) throws UserIdAlreadyExistsException, EmailAlreadyExistsException;
}
