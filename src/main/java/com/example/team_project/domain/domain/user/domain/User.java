package com.example.team_project.domain.domain.user.domain;

import com.example.team_project.enums.UserGrade;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserGrade userGrade;

    protected User() {
    }

    public User(String userId, String password, String userName, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public User(String userId, String password, String userName, String phoneNumber, UserGrade userGrade) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userGrade = userGrade;
    }

}