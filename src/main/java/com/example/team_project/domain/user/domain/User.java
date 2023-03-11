package com.example.team_project.domain.user.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // 아이디 : test123
    private String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

}