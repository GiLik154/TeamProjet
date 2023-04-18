package com.example.team_project.controller.core.user;

import lombok.Data;
import lombok.Getter;

@Getter
public class UserJoinControllerDto {

    private final String userId;
    private final String password;
    private final String userName;
    private final String phoneNumber;

    public UserJoinControllerDto(String userId, String password, String userName, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }
}
