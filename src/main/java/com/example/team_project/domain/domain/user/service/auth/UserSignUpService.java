package com.example.team_project.domain.domain.user.service.auth;


import com.example.team_project.controller.core.user.dto.UserJoinDTO;


public interface UserSignUpService {
    static class UidExistException extends Exception { }

    String join(UserJoinDTO userJoinDTO) throws UidExistException ;

}

