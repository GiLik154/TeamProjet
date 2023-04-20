package com.example.team_project.domain.domain.user.service.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignUpDTO {
    private String userId;

    private String password;

    private String userName;

    private String email;

    private String phoneNumber;

    private boolean del;

    private boolean social;

}