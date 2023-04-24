package com.example.team_project.domain.domain.user.service.auth;


import com.example.team_project.controller.core.user.dto.UserJoinDTO;
import com.example.team_project.domain.domain.user.dto.UserSignUpDto;


public interface UserSignUpService {
    static class UidExistException extends Exception { }

    void signUp(UserSignUpDto userSignUpDto) throws Exception ;

}

