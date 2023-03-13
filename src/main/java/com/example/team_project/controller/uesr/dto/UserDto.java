package com.example.team_project.controller.uesr.dto;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    @NotBlank(message = "아이디가 필요")
    private final String name;
    @NotBlank(message = "비밀번호 필요")
    @Min(8)
    @Max(12)
    private final String pass;

    public UserDto(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

}
