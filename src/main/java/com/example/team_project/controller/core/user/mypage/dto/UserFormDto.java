package com.example.team_project.controller.core.user.mypage.dto;


import lombok.Data;


@Data
public class UserFormDto {
    private Long id;

    private String userId;

    private String password;

    private String userName;

    private String email;

    private String phoneNumber;

    public UserFormDto(String userId, String password, String userName, String email, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UserFormDto() {

    }

    public UserFormDto(Long id, String password, String userName, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }
}
