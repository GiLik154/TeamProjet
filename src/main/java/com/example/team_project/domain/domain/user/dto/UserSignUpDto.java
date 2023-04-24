package com.example.team_project.domain.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserSignUpDto {

    private String userId;
    private String password;
    private String userName;
    private String email;
    private String phoneNumber;

    public UserSignUpDto(String userId, String password, String userName, String email, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
