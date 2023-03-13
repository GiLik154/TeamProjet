package com.example.team_project.domain.user.domain;

import com.example.team_project.domain.user.dto.PostDto;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="joinuser")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name; // 아이디 : test123
    @Column(nullable = false)
    private String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}