package com.example.team_project.domain.user.dto;

import lombok.Getter;

@Getter
public class UserDto {

    private String name;
    private String password;

    public UserDto(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
