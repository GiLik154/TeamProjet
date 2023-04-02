package com.example.team_project.domain.domain.user.domain;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.enums.UserGrade;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_grade")
    private UserGrade userGrade;

    public User(String userId, String password, String userName, String phoneNumber, UserGrade userGrade) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userGrade = userGrade;
    }

}