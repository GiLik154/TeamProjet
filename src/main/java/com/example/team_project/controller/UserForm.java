package com.example.team_project.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserForm {
    @NotEmpty(message = "아이디 입력은 필수 입니다.")
    private String name;

    private String password;
}
