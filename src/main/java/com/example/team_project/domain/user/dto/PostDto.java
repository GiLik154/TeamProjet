package com.example.team_project.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {

    private Long id;
    private String name; // 아이디 : test123
    private String password;
}
