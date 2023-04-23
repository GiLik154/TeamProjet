package com.example.team_project.controller.core.user.dto;

import lombok.Data;

@Data
public class UserJoinDTO {
    private String userId;
    private String password;
    private String userName;
    private String email;
    private String phoneNumber;
    private boolean del;
    private boolean social;
}
